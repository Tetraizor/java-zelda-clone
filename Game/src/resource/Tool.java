package resource;

import entity.Entity;

public class Tool extends Entity {

    public float swingTime;
    public float knockback;
    public int damage;
    public int index;

    public String name;

    public boolean isProjectileBased;

    public int imageIndex;

    public boolean isEnabled;

    public Tool(String _name, float _swingTime, int _damage, float _knockback, boolean _isProjectileBased, int _imageIndex, int _index) {
        name = _name;
        swingTime = _swingTime;
        damage = _damage;
        isProjectileBased = _isProjectileBased;
        imageIndex = _imageIndex;
        index = _index;
        knockback = _knockback;
    }

    public void Enable() {
        isEnabled = true;
    }

    public void Disable() {
        isEnabled = false;
    }

}
