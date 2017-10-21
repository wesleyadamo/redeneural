import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class Leitor {

	public double[][] entrada() {
		try {
			FileReader arq = new FileReader("Dados.txt");
			@SuppressWarnings("resource")
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine(); // lê a primeira linha
			// a variável "linha" recebe o valor "null" quando o processo
			// de repetição atingir o final do arquivo texto

			String[] texto;

			double[][] entrada = new double[150][5];
			int k = 1;

			DecimalFormat df = new DecimalFormat("0.####");

			linha = lerArq.readLine();

			while (linha != null && k <= 150) {

				// //system.out.printf("%s\n", linha);
				texto = linha.split("\\s+");

				for (int i = 0; i < 5; i++) {
					// //system.out.println(texto[i]);

					if (i == 0) {
						if (texto[i].equals("2"))
							entrada[k - 1][i] = -1;
						else
							entrada[k - 1][i] = Double.parseDouble(texto[i]);

					} else {
						double v = Double.parseDouble(texto[i]);
						String v1 = df.format(v);

						entrada[k - 1][i] = Double.parseDouble(v1) / (double) 100;
					}

				}

				k++;

				linha = lerArq.readLine(); // lê da segunda até a última linha

			}

			return entrada;
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		return null;
	}

}
