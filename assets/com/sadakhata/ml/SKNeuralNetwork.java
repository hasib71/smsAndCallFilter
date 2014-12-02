/*
 * Author: Hasib Al Muhaimin.
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 */

package com.sadakhata.ml;

import java.io.*;
import android.content.res.AssetManager;

public class SKNeuralNetwork{
	NeuralNetwork neuralnetwork;
	//String dataFileName;
	
	
	
	/*
	public void saveData()
	{
		try{
			
			ObjectOutputStream objectoutputstream = new ObjectOutputStream(new FileOutputStream(dataFileName));
			
			objectoutputstream.writeObject(neuralnetwork);
		
		}catch(Exception ex)
		{
			System.out.println("<<<<<<<<< COULDN'T WRITE OBJECT >>>>>>>>>>>");
		}
	}
	*/

	public SKNeuralNetwork(int _inputUnit, int _outputUnit , int _hiddenLayer , int _neuronPerHiddenLayer, String _dataFileName, AssetManager assetmanager )
	{
		//dataFileName = _dataFileName;

		try{
			
			ObjectInputStream objectinputstream = new ObjectInputStream(assetmanager.open(_dataFileName));

			try{
				neuralnetwork = (NeuralNetwork) objectinputstream.readObject();
			}catch(Exception ex)
			{
				System.out.println("<<< COULDN'T READ OBJECT >>>");
				neuralnetwork = new NeuralNetwork(_inputUnit, _outputUnit, _hiddenLayer, _neuronPerHiddenLayer);
			}
		}catch(Exception ex)
		{
				ex.printStackTrace();
				System.out.println("<<< COULDN'T READ OBJECT >>>");
				neuralnetwork = new NeuralNetwork(_inputUnit, _outputUnit, _hiddenLayer, _neuronPerHiddenLayer);
		}
	}

	public int feedForward(double x[])
	{
		return neuralnetwork.feedForward(x);
	}

	public void backPropagate(double x[][], double t[][], int m)
	{
		neuralnetwork.backPropagate(x, t, m);
	}

	public int getNumTrained()
	{
		return neuralnetwork.numTrained;
	}
	
	public void printCostFunctionJ(double x[][], double t[][], int m)
	{
		neuralnetwork.calcError(x, t, m);
	}
	
}