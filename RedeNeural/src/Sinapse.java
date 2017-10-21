import java.util.Random;

public class Sinapse {
	
	
	double peso;
	public double pesoat;
	Random rand = new Random();
	double valor;
	Neuronio esquerda;
   


	
	public Sinapse(Neuronio esquerda) {
	    this.esquerda = esquerda;
	    peso =  rand.nextDouble();
	    pesoat= peso;
	    valor = esquerda.saida;
	    
		
	}
	
}
