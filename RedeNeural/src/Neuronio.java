import java.util.ArrayList;

public class Neuronio {

	double saida;
	ArrayList<Sinapse> sinapse = new ArrayList<Sinapse>();
	double erro;
	int num;

	public Neuronio(int n) {
		num = n;
	}

	public double saida() {

		double somatorio = 0;

		for (Sinapse s : sinapse) {
			somatorio += s.peso * s.esquerda.saida;

		}
		saida = funcaoAtivacao(somatorio);

		return saida;

	}

	public double funcaoAtivacao(double x) {

		return Math.tanh(x);
	}

}
