package it.epicode.m1.s3.g5;

public abstract class ElementoCatalogoII {
	private final String codiceISBN;
	private final String titolo;
	private final int annoPubblicazione;
	private final int numeroPagine;

	public ElementoCatalogoII(String codiceISBN, String titolo, int annoPubblicazione, int numeroPagine) {
	    this.codiceISBN = codiceISBN;
	    this.titolo = titolo;
	    this.annoPubblicazione = annoPubblicazione;
	    this.numeroPagine = numeroPagine;
	}

	public String getCodiceISBN() {
	    return codiceISBN;
	}

	public String getTitolo() {
	    return titolo;
	}

	public int getAnnoPubblicazione() {
	    return annoPubblicazione;
	}

	public int getNumeroPagine() {
	    return numeroPagine;
	}

	public abstract String getTipo();

	@Override
	public String toString() {
	    return codiceISBN + "," + titolo + "," + annoPubblicazione + "," + numeroPagine + "," + getTipo();
	}

}