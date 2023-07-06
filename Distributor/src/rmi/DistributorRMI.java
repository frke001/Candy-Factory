package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.RawMaterial;

public interface DistributorRMI extends Remote{
	
	List<RawMaterial> getAllRawMaterials() throws RemoteException;
	
	boolean buyRawMateral(int id, int quantity) throws RemoteException;

}
