package com.example.buensabor.Services.Impl;

import com.example.buensabor.Exceptions.StockException;
import com.example.buensabor.Models.Entity.Ingredient;
import com.example.buensabor.Models.Entity.Product;
import com.example.buensabor.Models.FixedEntities.MeasurementUnit;
import com.example.buensabor.Repositories.IngredientRepository;
import com.example.buensabor.Services.IngredientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl extends BaseServiceImpl<Ingredient,Long> implements IngredientService {

    private IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        super(ingredientRepository);
        this.ingredientRepository = ingredientRepository;
    }

    private double convertMeasureUnit(double quantity, MeasurementUnit measurementUnitFrom,MeasurementUnit measurementUnitTo){

        // Tabla de conversiones
        double gramoToKilogramo = 0.001;
        double kilogramoToGramo = 1000;
        double kilogramoToLitro = 1;
        double mililitroToGramo = 1;
        double litroToKilogramo = 1;

        // Realizar la conversión
        double resultado = quantity;

        if (measurementUnitFrom.equals(MeasurementUnit.g)) {
            if (measurementUnitTo.equals(MeasurementUnit.kg)) {
                resultado *= gramoToKilogramo;
            }
        } else if (measurementUnitFrom.equals(MeasurementUnit.kg)) {
            if (measurementUnitTo.equals(MeasurementUnit.g)) {
                resultado *= kilogramoToGramo;
            } else if (measurementUnitTo.equals(MeasurementUnit.l)) {
                resultado *= kilogramoToLitro;
            }
        } else if (measurementUnitFrom.equals(MeasurementUnit.ml)) {
            if (measurementUnitTo.equals(MeasurementUnit.g)) {
                resultado *= mililitroToGramo;
            }
        } else if (measurementUnitFrom.equals(MeasurementUnit.l)) {
            if (measurementUnitTo.equals(MeasurementUnit.kg)) {
                resultado *= litroToKilogramo;
            }
        }

        return resultado;
    }

    @Override
    public List<Ingredient> getByNameAndState(String name, String state) {
        return ingredientRepository.getByNameAndState(name,state);
    }

    @Override
    @Transactional
    public void decrementStock(Long ingredientId, double quantity, MeasurementUnit measurementUnit) {
        try{
            Optional<Ingredient> ing = ingredientRepository.findById(ingredientId);

            if (ing.get().getMeasurementUnit().equals(measurementUnit))
            {
                validateUsedStock(quantity, ing.get().getCurrentStock());
                ing.get().decrementStock(quantity);
                ingredientRepository.save(ing.get());
            }else{
                double convertedQuantity = convertMeasureUnit(quantity, measurementUnit, ing.get().getMeasurementUnit());
                validateUsedStock(convertedQuantity, ing.get().getCurrentStock());

                ing.get().decrementStock(convertedQuantity);
                ingredientRepository.save(ing.get());
            }
        }
        catch (StockException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println("Algo salio mal al disminuir el stock");
        }

    }

    @Override
    @Transactional
    public void incrementStock(Long ingredientId, double quantity, MeasurementUnit measurementUnit) {
        try{
            Optional<Ingredient> ing = ingredientRepository.findById(ingredientId);

            if (ing.get().getMeasurementUnit().equals(measurementUnit))
            {

                ing.get().addStock(quantity);
                ingredientRepository.save(ing.get());
            }else{
                double convertedQuantity = convertMeasureUnit(quantity, measurementUnit, ing.get().getMeasurementUnit());

                ing.get().addStock(convertedQuantity);
                ingredientRepository.save(ing.get());
            }
        }
        catch (Exception e){
            System.out.println("Algo salio mal al incrementar el stock");
        }
    }


    private void validateUsedStock(double quantity, double actualStock) throws StockException{
        if (quantity > actualStock)
            throw new StockException("la cantidad a consumir es mayor a la existente");
    }
}
