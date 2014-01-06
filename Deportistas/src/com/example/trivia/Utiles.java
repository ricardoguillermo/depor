package com.example.trivia;

public class Utiles {
	
	
		String orden;
		
		public String sortearPosibilidad() {

			switch ((int) (Math.random() * 24 + 1)) {
			case 1:
				orden = "c123";
				break;
			case 2:
				orden = "c132";
				break;
			case 3:
				orden = "c213";
				break;
			case 4:
				orden = "c231";
				break;
			case 5:
				orden = "1c23";
				break;
			case 6:
				orden = "12c3";
				break;
			case 7:
				orden = "123c";
				break;
			case 8:
				orden = "21c3";
				break;
			case 9:
				orden = "213c";
				break;
			case 10:
				orden = "231c";
				break;
			case 11:
				orden = "23c1";
				break;
			case 12:
				orden = "312c";
				break;
			case 13:
				orden = "31c2";
				break;
			case 14:
				orden = "321c";
				break;
			case 15:
				orden = "32c1";
				break;
			case 16:
				orden = "3c12";
				break;
			case 17:
				orden = "3c21";
				break;
			case 18:
				orden = "2c31";
				break;
			case 19:
				orden = "2c13";
				break;
			case 20:
				orden = "c321";
				break;
			case 21:
				orden = "c312";
				break;
			case 22:
				orden = "1c32";
				break;
			case 23:
				orden = "13c2";
				break;
			case 24:
				orden = "132c";
				break;
			}
			return orden;
		}
		// coloca frases para elogiar en joda
	public String frasesElogiosas(){
		String frase="Brillante";
		switch ((int) (Math.random() * 24 + 1))	{
			case 1:
				frase = "Muy bien";
				break;
			case 2:
				frase = "Buen trabajo!";
				break;
			case 3:
				frase = " Asi se hace";
				break;
			case 4:
				frase = "Super bien";
				break;
			case 5:
				frase = "Eres especial";
				break;
			case 6:
				frase = "Excelente";
				break;
			case 7:
				frase = "Que estupendo";
				break;
			case 8:
				frase = "Bien";
				break;
			case 9:
				frase = "Formidable";
				break;
			case 10:
				frase = "Estamos muy orgullosa de vos";
				break;
			case 11:
				frase = "Sabia que podias hacerlo";
				break;
			case 12:
				frase = "Fantastico";
				break;
			case 13:
				frase = "Vas muy bien";
				break;
			case 14:
				frase = "Correcto";
				break;
			case 15:
				frase = "Bravo";
				break;
			case 16:
				frase = "Eres Increible";
				break;
			case 17:
				frase = "Esa es la manera";
				break;
			case 18:
				frase = "Vas por buen camino";
				break;
			case 19:
				frase = "Che, que inteligente sos!";
				break;
			case 20:
				frase = "Vos sos bueno en esto";
				break;
			case 21:
				frase = "Nada se te escapa";
				break;
			case 22:
				frase = "Mejor que Obdulio en el 50";
				break;
			case 23:
				frase = "Maestro sos lo máximo";
				break;
			case 24:
				frase = "Bien,Se ve que se que sabes del tema";
				break;
				
		}
		return frase;
		}
}






