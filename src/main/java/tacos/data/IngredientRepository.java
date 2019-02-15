package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne();

    Ingredient save(Ingredient ingredient);
}
