# CasualQuestJ
Basic RPG combat demo using assets from [Casual Quest BYOND](http://www.byond.com/games/iainperegrine/casualquest).

![](2016-02-24_00-29-03.gif)

### Controls

* Move: Arrow keys
* Attack: Space
* Skills: 1-Z, 2-X, 3-C
* Reset: R
* Pause: P

### Items

Walk over them to pick them up.

They blink and disappear after 10 seconds. 

* ![](rsc/items/cherry.png) Cherry: Heals you for 1 health. Can't be picked up if your health is full. 
* ![](rsc/items/gold1.png) Gold Coin: Adds 1 to your Gold count. Pretty much useless, though.
* ![](rsc/items/bottle.png) Bottle: Fills your aura completely. 
* ![](rsc/items/plum.png) Plum: Fills your health completely. 
* ![](rsc/items/shield.png) Shield: Grants invincibility for 5 seconds. 

### Classes 
Press the number on your keyboard to change to it.

1. ![](rsc/classes/adventurer/east1.png) Adventurer 
  * Health: 2
  * Primary: ![](rsc/weapons/sword3.png) Wooden sword (1 damage)
2. ![](rsc/classes/knight/east1.png) Knight 
  * Health: 8
  * Primary: ![](rsc/weapons/metalSword3.png) Steel sword (2 damage)
3. ![](rsc/classes/mage/east1.png) Mage 
  * Health: 4
  * Aura: 4 (regen: 1 aura per second)
  * Primary: ![](rsc/weapons/fireball1.png) Fireball (mid-ranged, 1 damage, ranged)
  * Skill 1: ![](rsc/weapons/bigFire1.png) Big Fireball (long-ranged, 2 damage, 2 aura) 
  * Skill 2: ![](rsc/weapons/seeker1.png) Seeker (long-ranged, 2 damage, 3 aura) This magical orb chases nearby enemies.
  * Skill 3: ![](rsc/weapons/bigFire1.png) Explosion (mid-ranged, 2 damage per fireball, 4 aura) Explosion magic is best magic. This launches 12 Big Fireballs all around you, but leaves you vulnerable afterwards. 

### Enemies

All enemies deal damage on contact with the player.

Enemies spawn from the edges of the map. Keep your distance from the edges. 

* __Bug__

  Moves slowly in cardinal directions. 
  * ![](rsc/enemies/basic%20bug/east1.png) 
    * Health: 1
    * Damage: 1
  * ![](rsc/enemies/medium%20bug/east1.png) 
    * Health: 2
    * Damage: 1
  * ![](rsc/enemies/strong%20bug/east1.png) 
    * Health: 4
    * Damage: 2
* __Bird__ 

 Moves in straight diagonal lines pretty quickly and bounces on the sides.
  * ![](rsc/enemies/bird/east1.png)
    * Health: 1
    * Damage: 1
