public class Entity {
    public Vector2 position;
    public Vector2 velocity;
    
    public Entity(Vector2 initialPosition) {
        position = initialPosition;
        velocity = new Vector2();
        Game.createdEntities.add(this);
    }
    
    public void destroy() {
        Game.destroyedEntities.add(this);
    }
    
    public void update() { 
        position = position.add(velocity);
    }
    
    public void draw() { }
}