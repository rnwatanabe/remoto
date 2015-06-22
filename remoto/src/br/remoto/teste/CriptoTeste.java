package br.remoto.teste;

public class CriptoTeste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String senha = "cortomaltes";
		String cripto = "";
		
		for(int i = 0; i < senha.length();i++)
		{
			char c = senha.charAt(i);
			
			int dig = c * c;
			
			cripto += dig;
		}
		
		System.out.print(cripto);
		
		
		
	}

}
