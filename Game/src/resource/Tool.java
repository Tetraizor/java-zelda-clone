package resource;

import entity.Entity;

public class Tool extends Entity {

    public float swingTime;
    public int damage;

    public String name;

    public boolean isProjectileBased;

    public int imageIndex;

    public boolean isEnabled;

    public Tool(String _name, float _swingTime, int _damage, boolean _isProjectileBased, int _imageIndex) {
        name = _name;
        swingTime = _swingTime;
        damage = _damage;
        isProjectileBased = _isProjectileBased;
        imageIndex = _imageIndex;
    }

    public void Enable() {
        isEnabled = true;
    }

    public void Disable() {
        isEnabled = false;
    }

}
