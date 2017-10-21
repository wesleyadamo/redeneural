import java.util.ArrayList;

public class RedeNeural {

	ArrayList<Neuronio> camadaEntrada = new ArrayList<Neuronio>();
	ArrayList<Neuronio> camadaOculta = new ArrayList<Neuronio>();
	ArrayList<Neuronio> camadaSaida = new ArrayList<Neuronio>();

	int acertos = 0;
	int erros = 0;
	double erroParada = 0.000000000000000000000000000001;

	int tamanhoTreinamento = 120;

	// double taxa = 0.0455;

	double taxa = 0.0225;

	public void criarRede(int numEntrada, int numOculta, int numSaida) {

		int num = 0;
		for (int i = 0; i < numEntrada; i++) {
			camadaEntrada.add(new Neuronio(num++));
		}

		for (int i = 0; i < numOculta; i++) {
			camadaOculta.add(new Neuronio(num++));
		}

		for (int i = 0; i < numSaida; i++) {
			camadaSaida.add(new Neuronio(num++));
		}

		for (Neuronio e : camadaEntrada) {
			for (Neuronio oc : camadaOculta) {
				Sinapse s = new Sinapse(e);

				oc.sinapse.add(s);
			}

		}

		for (Neuronio s : camadaSaida) {
			for (Neuronio oc : camadaOculta) {
				Sinapse ss = new Sinapse(oc);

				s.sinapse.add(ss);
			}

		}

	}

	public double treinar(double c[], double v) {

		int i = 0;
		for (Neuronio en : camadaEntrada) {
			en.saida = c[i];
			i++;

		}

		for (Neuronio s : camadaOculta) {
			s.saida();
		}

		for (Neuronio s : camadaSaida) {

			s.saida();
		}

		camadaSaida.get(0).erro = erroCamadaSaida(v);

		erroCamadaOculta();

		atualizarPesosSaida();
		pesosCamadaOculta();

		return erroDaRede();

	}

	public void pesosCamadaOculta() {
		for (Neuronio oc : camadaOculta) {
			for (Sinapse s : oc.sinapse) {

				s.peso = s.peso + taxa * s.esquerda.saida * oc.erro;
			}
		}
	}

	public double erroDaRede() {
		// return 0.5 * Math.pow(camadaSaida.get(0).erro, 2);
		return camadaSaida.get(0).erro;
	}

	public double erroCamadaSaida(double v) {

		return (v - camadaSaida.get(0).saida) * derivadaFuncao(camadaSaida.get(0).saida);

	}

	public void atualizarPesosSaida() {
		for (Neuronio oc : camadaSaida) {
			for (Sinapse s : oc.sinapse) {
				s.peso = s.peso + taxa * s.esquerda.saida * oc.erro;
			}
		}
	}

	public void erroCamadaOculta() {

		for (Neuronio oc : camadaSaida) {
			for (Sinapse s : oc.sinapse) {
				s.esquerda.erro = derivadaFuncao(s.esquerda.saida) * oc.erro * s.peso;
			}
		}

	}

	public void mostrarRede() {

		for (Neuronio oc : camadaSaida) {
			System.out.println();
			System.out.println(oc.num);
			System.out.println("QUantidade de sinapse: " + oc.sinapse.size());
			for (Sinapse n : oc.sinapse) {
				System.out.print(" " + n.esquerda.num);
			}

		}
	}

	public double derivadaFuncao(double value) {
		return (1 - Math.pow(value, 2));
	}

	public void executar(double[] c, double esp) {

		int i = 0;
		for (Neuronio en : camadaEntrada) {
			en.saida = c[i];
			i++;

		}

		for (Neuronio s : camadaOculta) {
			s.saida();
		}

		for (Neuronio s : camadaSaida) {
			double saida = s.saida();
			if (Math.round(saida) == esp) {
				acertos++;
				System.out.println("VALOR DE SAIDA : " + saida + " Esperado " + esp);

			} else {
				erros++;
				System.out.println("VALOR DE SAIDA : " + saida + " Esperado " + esp);
			}

		}

	}

	public void treinar(double[][] dadosTreinamento) {

		double erro = 1;

		System.out.println("TREINANDO A REDE");
		long time1 = System.currentTimeMillis();

		double[] treinamento = new double[4];
		int eras = 0;
		while (Math.abs(erro) > erroParada) {
			eras++;
			for (int i = 0; i < tamanhoTreinamento; i++) {
				for (int j = 1; j < 5; j++) {
					treinamento[j - 1] = dadosTreinamento[i][j];
				}

				erro = treinar(treinamento, dadosTreinamento[i][0]);

				if (Math.abs(erro) < erroParada) {
					break;

				}
			}

		}

		long time2 = System.currentTimeMillis();
		System.out.println("...................");
		System.out.println("FIM DO TREINAMENTO");
		System.out.println("TEMPO DE TREINAMENTO: " + (time2 - time1) + " ms");
		System.out.println("ERAS: " + eras);
		System.out.println("=============================");

		System.out.println();

	

	}

	public void testar(double dadosTeste[][]) {

		System.out.println("SAIDA\t\t\t\tESPERADO\tSAÍDA ERRADA");
		System.out.println("===================================================================");

		double[] treinamento = new double[5];
		for (int i = tamanhoTreinamento; i < 150; i++) {
			for (int ll = 0; ll < 5; ll++) {
				treinamento[ll] = dadosTeste[i][ll];
			}

			int j = 1;
			for (Neuronio en : camadaEntrada) {
				en.saida = treinamento[j];
				j++;

			}

			for (Neuronio s : camadaOculta) {
				s.saida();
			}

			for (Neuronio s : camadaSaida) {
				double saida = s.saida();
				double diferenca = treinamento[0] - saida;
				if (Math.abs(diferenca) <= 0.05) {
					acertos++;
					System.out
							.println(+ saida + "\t\t" + treinamento[0] + " \t" );

				} else {
					erros++;
					System.out.println(
							+ saida + "\t\t"+ treinamento[0] + "\t\t     x");
				}

			}

		}

		System.out.println("\nFIM DO TESTE");
		System.out.println("===================================================================");

	}

	public static void main(String[] args) {
		RedeNeural rn = new RedeNeural();

		int tamCamadaEntrada = 4;
		int tamCamadaOculta = 4;
		int tamCamadaSaida = 1;
		rn.criarRede(tamCamadaEntrada, tamCamadaOculta, tamCamadaSaida);

		System.out.println("INFORMAÇÕES DA REDE NEURAL");
		System.out.println("=============================");
		System.out.println("QUANTIDADE NEURÔNIOS NA ENTRADA: " + tamCamadaEntrada);
		System.out.println("QUANTIDADE NEURÔNIOS NA OCULTA: " + tamCamadaOculta);
		System.out.println("QUANTIDADE NEURÔNIOS NA SAIDA: " + tamCamadaSaida);
		System.out.println("TAXA DE APRENDIZAGEM: " + rn.taxa);
		System.out.println("ERRO DE PARADA: " + rn.erroParada);
		System.out.println();
		System.out.println("=============================");

		Leitor l = new Leitor();

		double[][] en = l.entrada();

		rn.treinar(en);
		rn.testar(en);

		double porcentagemAcerto = Math.round(((100 * rn.acertos) / (150d - rn.tamanhoTreinamento)));

		System.out.println();
		System.out.println("ESTATÍSTICA DA REDE");
		System.out.println("=============================");
		System.out.println("ACERTOS\t%\tErros\t%");
		System.out.println(rn.acertos + "\t" + porcentagemAcerto + "\t" + rn.erros + "\t" + (100 - porcentagemAcerto));

	}
}
