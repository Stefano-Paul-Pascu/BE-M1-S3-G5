package it.epicode.m1.s3.g5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogoBibliotecarioII {

    private static final String FILE_PATH = "C:\\Users\\STEFANO\\Desktop\\biblioteca.txt";

    private List<ElementoCatalogoII> catalogo;

    public CatalogoBibliotecarioII() {
        catalogo = new ArrayList<>();
    }

    public void aggiungiElemento(ElementoCatalogoII elemento) {
        catalogo.add(elemento);
    }

    public Optional<ElementoCatalogoII> rimuoviElemento(String codiceISBN) {
        Optional<ElementoCatalogoII> elemento = cercaElementoPerISBN(codiceISBN);
        elemento.ifPresent(catalogo::remove);
        return elemento;
    }

    public Optional<ElementoCatalogoII> cercaElementoPerISBN(String codiceISBN) {
        return catalogo.stream()
                .filter(elemento -> elemento.getCodiceISBN().equals(codiceISBN))
                .findFirst();
    }

    public List<ElementoCatalogoII> cercaElementiPerAnnoPubblicazione(int anno) {
        return catalogo.stream()
                .filter(elemento -> elemento.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    public List<ElementoCatalogoII> cercaElementiPerAutore(String autore) {
        return catalogo.stream()
                .filter(elemento -> elemento instanceof LibroII && ((LibroII) elemento).getAutore().equals(autore))
                .collect(Collectors.toList());
    }

    public void salvaCatalogoSuFile() throws IOException {
        Path path = Paths.get(FILE_PATH);
        List<String> lines = catalogo.stream()
                .map(ElementoCatalogoII::toString)
                .collect(Collectors.toList());
        Files.write(path, lines);
    }

    public void caricaCatalogoDaFile() throws IOException {
        Path path = Paths.get(FILE_PATH);
        List<String> lines = Files.readAllLines(path);
        catalogo = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            String codiceISBN = fields[0];
            String titolo = fields[1];
            int annoPubblicazione = Integer.parseInt(fields[2]);
            int numeroPagine = Integer.parseInt(fields[3]);
            String tipo = fields[4];
            if (tipo.equals("Libro")) {
                String autore = fields[5];
                String genere = fields[6];
                LibroII libro = new LibroII(codiceISBN, titolo, annoPubblicazione, numeroPagine, autore, genere);
                catalogo.add(libro);
            } else if (tipo.equals("Rivista")) {
                String periodicità = fields[5];
                RivistaII rivista = new RivistaII(codiceISBN, titolo, annoPubblicazione, numeroPagine, periodicità);
                catalogo.add(rivista);
            } else {
                throw new IllegalArgumentException("Tipo di elemento non valido: " + tipo);
            }
        }
    }
}
