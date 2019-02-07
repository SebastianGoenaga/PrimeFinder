/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {

	private final static int NTHREADS = 10;
	private final static int MAXVALUE = 30000000;
	private final static int TMILISECONDS = 5000;

	private final int NDATA = MAXVALUE / NTHREADS;

	private PrimeFinderThread pft[];
	private Boolean flag;

	private Control() {
		super();
		this.pft = new PrimeFinderThread[NTHREADS];
		flag = true;

		int i;
		for (i = 0; i < NTHREADS - 1; i++) {
			PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA, this);
			pft[i] = elem;
		}
		pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1, this);
	}

	public Boolean getFlag() {
		return flag;
	}

	public static Control newControl() {
		return new Control();
	}

	@Override
	public void run() {
		for (int i = 0; i < NTHREADS; i++) {
			pft[i].start();
		}
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				Thread.sleep(1000);
				flag = false;
				for (PrimeFinderThread p : pft) {
					System.out.println(p.getPrimes().size());
				}
				System.out.println();
				System.out.println("Presione enter para continuar calculando primos");
				System.out.println();
				sc.nextLine();
				flag = true;
				synchronized (this) {
					this.notifyAll();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
