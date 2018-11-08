package org.nico.noson.adapter.type;


public class TypeAdapter_Character extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		if(Character.class.isAssignableFrom(target.getClass())) {
			return (Character) target;
		}else {
			return new Character(String.valueOf(target).toCharArray()[0]);
		}
	}

}
