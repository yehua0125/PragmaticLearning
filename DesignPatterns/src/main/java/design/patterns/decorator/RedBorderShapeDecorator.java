package design.patterns.decorator;

public class RedBorderShapeDecorator extends ShapeDecorator {

    public RedBorderShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        super.draw();
        setRedBorder(decoratedShape);
    }

    private void setRedBorder(Shape shape) {
        System.out.println("Red border " + shape);
    }
    
    

}
