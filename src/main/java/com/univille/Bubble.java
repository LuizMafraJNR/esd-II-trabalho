package com.univille;

import java.util.ArrayList;
import java.util.List;

public class Bubble {
	public static void main(String[] args) {
		int[] tamanhos = {10000, 50000, 100000};
		int repeticoes = 10;

		String[] headers = {
			"Tamanho", "BubbleSort", "BubbleSort Melhorado", "QuickSort", "InsertionSort",
			"SelectionSort", "ShellSort", "HeapSort", "MergeSort", "RadixSort", "CountingSort", "BucketSort"
		};

		Object[][] resultados = new Object[tamanhos.length][headers.length];
		int linha = 0;

		for (int tamanho : tamanhos) {
			System.out.println("Tamanho do vetor: " + tamanho);

			long tempoBubble = 0, tempoBubbleMelhorado = 0, tempoQuickSort = 0, tempoInsertionSort = 0;
			long tempoSelectionSort = 0, tempoShellSort = 0, tempoHeapSort = 0, tempoMergeSort = 0;
			long tempoRadixSort = 0, tempoCountingSort = 0, tempoBucketSort = 0;

			for (int k = 0; k < repeticoes; k++) {
				int[] vetorOriginal = gerarVetorAleatorio(tamanho);

				int[] vetor = vetorOriginal.clone();
				long inicio = System.currentTimeMillis();
				bubbleSort(vetor);
				long fim = System.currentTimeMillis();
				tempoBubble += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				bubbleSortMelhorado(vetor);
				fim = System.currentTimeMillis();
				tempoBubbleMelhorado += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				quickSort(vetor, 0, vetor.length - 1);
				fim = System.currentTimeMillis();
				tempoQuickSort += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				insertionSort(vetor);
				fim = System.currentTimeMillis();
				tempoInsertionSort += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				selectionSort(vetor);
				fim = System.currentTimeMillis();
				tempoSelectionSort += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				shellSort(vetor);
				fim = System.currentTimeMillis();
				tempoShellSort += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				heapSort(vetor);
				fim = System.currentTimeMillis();
				tempoHeapSort += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				mergeSort(vetor, 0, vetor.length - 1);
				fim = System.currentTimeMillis();
				tempoMergeSort += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				radixSort(vetor);
				fim = System.currentTimeMillis();
				tempoRadixSort += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				countingSort(vetor);
				fim = System.currentTimeMillis();
				tempoCountingSort += (fim - inicio);

				vetor = vetorOriginal.clone();
				inicio = System.currentTimeMillis();
				bucketSort(vetor);
				fim = System.currentTimeMillis();
				tempoBucketSort += (fim - inicio);
			}

			resultados[linha][0] = tamanho;
			resultados[linha][1] = tempoBubble / repeticoes;
			resultados[linha][2] = tempoBubbleMelhorado / repeticoes;
			resultados[linha][3] = tempoQuickSort / repeticoes;
			resultados[linha][4] = tempoInsertionSort / repeticoes;
			resultados[linha][5] = tempoSelectionSort / repeticoes;
			resultados[linha][6] = tempoShellSort / repeticoes;
			resultados[linha][7] = tempoHeapSort / repeticoes;
			resultados[linha][8] = tempoMergeSort / repeticoes;
			resultados[linha][9] = tempoRadixSort / repeticoes;
			resultados[linha][10] = tempoCountingSort / repeticoes;
			resultados[linha][11] = tempoBucketSort / repeticoes;

			linha++;
		}

		ExcelExporter.exportarResultados(headers, resultados);
	}

	public static int[] gerarVetorAleatorio(int tamanho) {
		int[] vetor = new int[tamanho];
		for (int i = 0; i < vetor.length; i++) {
			vetor[i] = (int) (Math.random() * tamanho);
		}
		return vetor;
	}

	public static void bubbleSort(int[] vetor) {
		for (int i = 0; i < vetor.length; i++) {
			for (int j = 0; j < vetor.length - 1; j++) {
				if (vetor[j] > vetor[j + 1]) {
					int aux = vetor[j];
					vetor[j] = vetor[j + 1];
					vetor[j + 1] = aux;
				}
			}
		}
	}

	public static void bubbleSortMelhorado(int[] vetor) {
		boolean trocou;
		for (int i = 0; i < vetor.length - 1; i++) {
			trocou = false;
			for (int j = 0; j < vetor.length - 1 - i; j++) {
				if (vetor[j] > vetor[j + 1]) {
					int aux = vetor[j];
					vetor[j] = vetor[j + 1];
					vetor[j + 1] = aux;
					trocou = true;
				}
			}
			if (!trocou) break;
		}
	}

	public static void radixSort(int[] vetor) {
		int maior = obterMaior(vetor);
		for (int exp = 1; maior / exp > 0; exp *= 10) {
			countingSortPorDigito(vetor, exp);
		}
	}

	private static int obterMaior(int[] vetor) {
		int maior = vetor[0];
		for (int i = 1; i < vetor.length; i++) {
			if (vetor[i] > maior) {
				maior = vetor[i];
			}
		}
		return maior;
	}

	private static void countingSortPorDigito(int[] vetor, int exp) {
		int[] output = new int[vetor.length];
		int[] count = new int[10];

		for (int i = 0; i < vetor.length; i++) {
			count[(vetor[i] / exp) % 10]++;
		}

		for (int i = 1; i < 10; i++) {
			count[i] += count[i - 1];
		}

		for (int i = vetor.length - 1; i >= 0; i--) {
			output[count[(vetor[i] / exp) % 10] - 1] = vetor[i];
			count[(vetor[i] / exp) % 10]--;
		}

		System.arraycopy(output, 0, vetor, 0, vetor.length);
	}

	public static void countingSort(int[] vetor) {
		int maior = obterMaior(vetor);
		int[] count = new int[maior + 1];
		int[] output = new int[vetor.length];

		for (int i = 0; i < vetor.length; i++) {
			count[vetor[i]]++;
		}

		for (int i = 1; i <= maior; i++) {
			count[i] += count[i - 1];
		}

		for (int i = vetor.length - 1; i >= 0; i--) {
			output[count[vetor[i]] - 1] = vetor[i];
			count[vetor[i]]--;
		}

		System.arraycopy(output, 0, vetor, 0, vetor.length);
	}

	public static void quickSort(int[] vetor, int inicio, int fim) {
		if (inicio < fim) {
			int pivoIndex = particiona(vetor, inicio, fim);
			quickSort(vetor, inicio, pivoIndex - 1);
			quickSort(vetor, pivoIndex + 1, fim);
		}
	}

	private static int particiona(int[] vetor, int inicio, int fim) {
		int pivo = vetor[fim];
		int i = inicio - 1;
		for (int j = inicio; j < fim; j++) {
			if (vetor[j] <= pivo) {
				i++;
				int aux = vetor[i];
				vetor[i] = vetor[j];
				vetor[j] = aux;
			}
		}
		int aux = vetor[i + 1];
		vetor[i + 1] = vetor[fim];
		vetor[fim] = aux;
		return i + 1;
	}

	public static void insertionSort(int[] vetor) {
		for (int i = 1; i < vetor.length; i++) {
			int chave = vetor[i];
			int j = i - 1;
			while (j >= 0 && vetor[j] > chave) {
				vetor[j + 1] = vetor[j];
				j--;
			}
			vetor[j + 1] = chave;
		}
	}

	public static void selectionSort(int[] vetor) {
		for (int i = 0; i < vetor.length - 1; i++) {
			int menorIndex = i;
			for (int j = i + 1; j < vetor.length; j++) {
				if (vetor[j] < vetor[menorIndex]) {
					menorIndex = j;
				}
			}
			int aux = vetor[menorIndex];
			vetor[menorIndex] = vetor[i];
			vetor[i] = aux;
		}
	}

	public static void shellSort(int[] vetor) {
		for (int gap = vetor.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < vetor.length; i++) {
				int temp = vetor[i];
				int j;
				for (j = i; j >= gap && vetor[j - gap] > temp; j -= gap) {
					vetor[j] = vetor[j - gap];
				}
				vetor[j] = temp;
			}
		}
	}

	public static void heapSort(int[] vetor) {
		for (int i = vetor.length / 2 - 1; i >= 0; i--) {
			heapify(vetor, vetor.length, i);
		}
		for (int i = vetor.length - 1; i > 0; i--) {
			int aux = vetor[0];
			vetor[0] = vetor[i];
			vetor[i] = aux;
			heapify(vetor, i, 0);
		}
	}

	private static void heapify(int[] vetor, int tamanho, int raiz) {
		int maior = raiz;
		int esquerda = 2 * raiz + 1;
		int direita = 2 * raiz + 2;

		if (esquerda < tamanho && vetor[esquerda] > vetor[maior]) maior = esquerda;
		if (direita < tamanho && vetor[direita] > vetor[maior]) maior = direita;

		if (maior != raiz) {
			int troca = vetor[raiz];
			vetor[raiz] = vetor[maior];
			vetor[maior] = troca;
			heapify(vetor, tamanho, maior);
		}
	}

	public static void mergeSort(int[] vetor, int inicio, int fim) {
		if (inicio < fim) {
			int meio = (inicio + fim) / 2;
			mergeSort(vetor, inicio, meio);
			mergeSort(vetor, meio + 1, fim);
			merge(vetor, inicio, meio, fim);
		}
	}

	private static void merge(int[] vetor, int inicio, int meio, int fim) {
		int n1 = meio - inicio + 1;
		int n2 = fim - meio;

		int[] esquerda = new int[n1];
		int[] direita = new int[n2];

		for (int i = 0; i < n1; i++) esquerda[i] = vetor[inicio + i];
		for (int i = 0; i < n2; i++) direita[i] = vetor[meio + 1 + i];

		int i = 0, j = 0, k = inicio;
		while (i < n1 && j < n2) {
			if (esquerda[i] <= direita[j]) {
				vetor[k++] = esquerda[i++];
			} else {
				vetor[k++] = direita[j++];
			}
		}

		while (i < n1) vetor[k++] = esquerda[i++];
		while (j < n2) vetor[k++] = direita[j++];
	}

	public static void bucketSort(int[] vetor) {
		int maior = obterMaior(vetor);
		int numBaldes = maior / 10 + 1;
		List<List<Integer>> baldes = new ArrayList<>();

		for (int i = 0; i < numBaldes; i++) {
			baldes.add(new ArrayList<>());
		}

		for (int valor : vetor) {
			int indiceBalde = valor / 10;
			baldes.get(indiceBalde).add(valor);
		}

		int index = 0;
		for (List<Integer> balde : baldes) {
			balde.sort(null);
			for (int valor : balde) {
				vetor[index++] = valor;
			}
		}
	}
}
