package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import model.RawMaterial;

public class DistributorRMIImpl implements DistributorRMI{

	private ObservableList<RawMaterial> rawMaterials;
	
	public DistributorRMIImpl(ObservableList<RawMaterial> rawMaterials) {
		this.rawMaterials = rawMaterials;
	}
	@Override
	public List<RawMaterial> getAllRawMaterials() throws RemoteException {
		//List<RawMaterial> result = new ArrayList<>(rawMaterials);
		return rawMaterials.stream().collect(Collectors.toList());
		//return result;
	}

	@Override
	public boolean buyRawMateral(int id, int quantity) throws RemoteException {
		RawMaterial rawMaterial = new RawMaterial();
		rawMaterial.setId(id);
		 var index = rawMaterials.indexOf(rawMaterial);
         if (index != -1) {
        	 RawMaterial material = rawMaterials.get(index);
        	 
             if (material.getQuantity() >= quantity)
             {
            	 rawMaterials.remove(material);
            	 material.setQuantity(material.getQuantity() - quantity);
            	 rawMaterials.add(material);
                 return true;
             }
             else
                 return false;
         } else
             return false;
     }
}
