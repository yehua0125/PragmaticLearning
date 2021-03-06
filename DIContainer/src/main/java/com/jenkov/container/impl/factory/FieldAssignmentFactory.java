/*
    Copyright 2007-2010 Jenkov Development

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/



package com.jenkov.container.impl.factory;

import com.jenkov.container.itf.factory.FactoryException;
import com.jenkov.container.itf.factory.ILocalFactory;

import java.lang.reflect.Field;

/**

 */
public class FieldAssignmentFactory extends LocalFactoryBase implements ILocalFactory {

    protected Field          field                        = null;
    protected Class          fieldOwningClass             = null;
    protected ILocalFactory       fieldAssignmentTargetFactory = null;
    protected ILocalFactory       assignmentValueFactory       = null;

    public FieldAssignmentFactory(Field field, ILocalFactory assignmentTargetFactory, ILocalFactory assignmentValueFactory) {
        this.field                        = field;
        this.fieldAssignmentTargetFactory = assignmentTargetFactory;
        this.assignmentValueFactory       = assignmentValueFactory;
    }

    public FieldAssignmentFactory(Field field, Class fieldOwningClass, ILocalFactory assignmentValueFactory) {
        this.field = field;
        this.fieldOwningClass = fieldOwningClass;
        this.assignmentValueFactory = assignmentValueFactory;
    }

    public Class getReturnType() {
        return this.field.getType();
    }

    /* todo clean up this method. Field can never be void return types. Fields always have a type. */
    public Object instance(Object[] parameters, Object[] localProducts) {
        Object value = this.assignmentValueFactory.instance(parameters, localProducts);
        try {
            if(isInstanceField()) {
                field.set(this.fieldAssignmentTargetFactory.instance(parameters, localProducts), value);
                return value;
            }

            field.set(null, value);
            return value;
        } catch (Throwable t){
            throw new FactoryException(
                    "FieldAssignmentFactory", "ERROR_FLYWEIGHT_KEY_PARAMETER",                    
                    "Error setting field value " + value + " on field " + this.field, t);
        }
    }

    private boolean isInstanceField() {
        return this.fieldAssignmentTargetFactory != null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<FieldAssignmentFactory: ");
        builder.append(field);
        builder.append("> --> ");
        if(isInstanceField()){
            builder.append(this.fieldAssignmentTargetFactory);
        } else {
            builder.append("<");
            builder.append(this.fieldOwningClass);
            builder.append(">");
        }

        return builder.toString();
    }

    //this method is added only for testability. It is not part of the IFactory interface.
    public ILocalFactory getFieldAssignmentTargetFactory() {
        return fieldAssignmentTargetFactory;
    }

    public Field getField() {
        return field;
    }

    private boolean isVoidReturnType() {
        return void.class.equals(this.field.getType()) || this.field.getType() == null;
    }

}
