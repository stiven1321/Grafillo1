import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

public class grafo {

	static ArrayList<Integer> mayores = new ArrayList<>();
	static ArrayList<Integer> menores = new ArrayList<>();
	static int menorRest;
	static int indiceOld;

	public static void main(String[] args) {
		// int[][] Grafo = ingresoDeGrafo();
		// int[][] Grafo = { { 0, 1, 2, 3, 4 }, { 1, 0, 2, 5, 0 }, { 2, 0, 0, 4,
		// 6 }, { 3, 0, 0, 0, 7 },
		// { 4, 0, 0, 0, 0 } };

		 int[][] Grafo =
		 {{0,1,2,3,4,5,6,7,8},{1,0,8,5,0,0,0,0,0},{2,0,0,0,4,5,0,0,0},{3,0,0,0,0,0,4,0,0},{4,0,0,3,0,0,2,2,0},
		 {5,0,0,0,0,0,0,0,6},{6,0,0,0,0,0,0,3,6},{7,0,0,0,0,0,0,0,4},{8,0,0,0,0,0,0,0,0}};

		//int[][] Grafo = { { 0, 1, 2, 3, 4, 5, 6, 7 }, { 1, 0, 6, 4, 1, 0, 0, 0 }, { 2, 0, 0, 0, 0, 4, 0, 0 },
				//{ 3, 0, 0, 0, 3, 1, 3, 0 }, { 4, 0, 0, 0, 0, 0, 4, 0 }, { 5, 0, 0, 0, 0, 0, 0, 4 },
				//{ 6, 0, 0, 0, 0, 0, 0, 9 }, { 7, 0, 0, 0, 0, 0, 0, 0 } };

		for (int i = 0; i < Grafo.length; i++) {
			for (int j = 0; j < Grafo[0].length; j++) {
				System.out.print(Grafo[i][j] + ", ");
			}
			System.out.println();
		}
		System.out.println();

		flujoMax(Grafo, 1);

	}

	public static int[][] ingresoDeGrafo() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Cuantos Nodos Tiene Su Grafo??");
		int cantidadNodos = sc.nextInt();

		int[][] matriz = new int[cantidadNodos + 1][cantidadNodos + 1];
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (i == 0 && j == 0) {
					matriz[i][j] = 0;
				} else if (i == 0 && j != 0) {
					matriz[i][j] = j;
				} else if (i != 0 && j == 0) {
					matriz[i][j] = i;
				} else {
					matriz[i][j] = 0;
				}

			}
		}

		while (true) {
			System.out.println("Ingresar conexión, si no existen mas conexiones ingrese 0");
			System.out.println("Ingrese Nodo Origen: ");
			int o = sc.nextInt();
			if (o <= 0) {
				return matriz;
			} else {
				System.out.println("Ingrese Nodo Destino: ");
				int d = sc.nextInt();
				System.out.println("Ingrese Valor de la Arista: ");
				int v = sc.nextInt();

				matriz[o][d] = v;
			}
		}

	}

	public static void flujoMax(int[][] grafillo, int indice) {
		int mayor = 0;
		int i = indice;

		for (int j = 1; j < grafillo[0].length; j++) {
			int mayorTemp = grafillo[i][j];
			if (mayorTemp > mayor) {
				mayor = grafillo[i][j];
				indice = j;
			}
		}

		if (mayor != 0)
			mayores.add(mayor);

		if (mayor == 0 && i != (grafillo.length - 1) && i != 1) {
			grafillo[indiceOld][indice] = 0;
			mayores.clear();
			flujoMax(grafillo, 1);
		}

		if (mayor == 0 && i == 1) {
			int resultadoMax = 0;

			for (int j = 0; j < menores.size(); j++) {
				// System.out.println("va= "+menores.get(j));
				resultadoMax += menores.get(j);
			}

			System.out.println("El flujo maximo para este grafos es: " + resultadoMax);
			System.exit(0);
		}

		if (i < (grafillo.length - 1)) {
			indiceOld = i;
			flujoMax(grafillo, indice);
		} else {
			int menor = 1000000;
			for (int l = 0; l < mayores.size(); l++) {
				int menorTemp = mayores.get(l);
				if (menorTemp < menor) {
					menor = mayores.get(l);
					menorRest = menor;
				}

			}

			menores.add(menor);
			mayores.clear();

			indiceOld = i;
			newMatriz(grafillo, 1);
		}

	}

	public static void newMatriz(int[][] grafo, int indice) {
		int mayor = 0;
		int i = indice;

		for (int j = 1; j < grafo[0].length; j++) {
			int mayorTemp = grafo[i][j];
			if (mayorTemp > mayor) {
				mayor = grafo[i][j];
				indice = j;
			}
		}

		grafo[i][indice] = grafo[i][indice] - menorRest;
		if (grafo[i][indice] < 0) {
			grafo[i][indice] = 0;
		}

		if (i < (grafo.length - 1)) {
			newMatriz(grafo, indice);
		} else {
			for (int y = 0; y < grafo.length; y++) {
				for (int j = 0; j < grafo[0].length; j++) {
					System.out.print(grafo[y][j] + ", ");
				}
				System.out.println();
			}
			System.out.println();

			flujoMax(grafo, 1);

		}
	}

}
