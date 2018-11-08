package org.nico.noson.adapter.type;


public class TypeAdapter_char extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		if(char.class.isAssignableFrom(target.getClass())) {
			return (char) target;
		}else {
			return String.valueOf(target).toCharArray()[0];
		}
	}

}
