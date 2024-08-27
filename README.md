# ProjectileTrajectories
A Minecraft Paper plugin written in Kotlin allowing players to see the trajectories of different projectiles in real time.
![image](https://user-images.githubusercontent.com/44951826/177647850-74c5b76f-50d9-4b0a-9c77-ac13dc90108a.png)

## Mathematics of projectiles in Minecraft

The motion of projectiles in Minecraft is made up like this:
1. There's an initial velocity to a projectile:
   - Enderpearl, Snowball, Egg: 1.5
   - Bow, fully charged: 3.0
   - Trident, fully charged, unenchanted: 2.5
   - Crossbow: 3.15
2. Every tick, the projectile's velocity vector get's multiplied by a drag coefficient of 0.99
3. When the projectile is in water however, velocity of throwable projectiles (enderpearl, egg, snowball, experience bottle, potion) get's multiplied by 0.8 every tick, velocity of arrows gets multiplied by 0.6 every tick and velocity of tridents gets multiplied by 0.99 every tick
4. After drag has been applied, the gravitational acceleration is subtracted from the y-Component of the velocity vector every tick. Enderpearls, eggs and snowballs have a grvitational acceleration of 0.03. Thrown potions have a gravitational acceleration of 0.05 and experience bottles of 0.07. Arrows and tridents have a gravitational acceleration of 0.05000000074505806. This is the same in water and in air.

From this, we can derive the velocity in x and y direction (we assume projectile is in air). $v_0$ is the initial velocity and $\alpha$ is the angle, at which the projectile is thrown. $g$ is the gravitational acceleration of the projectile:
$$v_x(t)=v_0\cos(\alpha)\cdot 0.99^t$$

$v_y(t)$ is a bit more complicated though. We start with a recursive definition of the function based on the Minecraft code:
$$v_y(t)=(v_y(t-1)\cdot 0.99)-g \hspace{10pt} \text{where} \hspace{10pt} v_y(0)=v_0\sin(\alpha)$$

By solving this recurrence equation we get:
$$v_y(t)=0.99^t \cdot (100g+v_0\sin(\alpha))-100g$$

We can now get to the actual position equations by integration:
$$d_x(t)=-0.99^t \cdot \dfrac{v_0\cos(\alpha)}{\ln(100)-\ln(99)} + C$$
$$d_y(t)=-100gt-0.99^x \cdot \dfrac{v_0\sin(\alpha)+100g}{\ln(100)-\ln(99)} + C$$

We now have to find suitable constants of integration, so that $d_x(0)=0$ and $d_y(0)=0$:
$$d_x(t)=\dfrac{v_0\cos(\alpha)}{\ln(100)-\ln(99)}\cdot (1-0.99^t)$$
$$d_y(t)=\dfrac{v_0\sin(\alpha) + 100g}{\ln(100)-\ln(99)}\cdot\ (1-0.99^t)-100gt$$

We now solve for $t$ in the $d_x(t)$ equation:
$$t=\dfrac{\ln\left(1-x\cdot\dfrac{\ln(100)-\ln(99)}{v_0\cos(\alpha)}\right)}{\ln(0.99)}$$

Now we can substitute $t$ in the equation for $d_y(t)$.

The trajectory of a projectile in Minecraft with friction coefficient of $0.99/\frac{\text{1}}{\text{tick}}$ and
initial velocity $v_0/\frac{\text{blocks}}{\text{tick}}$ with angle $\alpha/\degree$ and gravitational acceleration of $g/\frac{\text{blocks}}{\text{tick}^2}$ can therefore be modeled by the following equation:

$$y(x)=\dfrac{v_0\sin(\alpha) + 100g}{\ln(100)-\ln(99)}\cdot\ (1-0.99^{\dfrac{\ln\left(1-x\cdot\dfrac{\ln(100)-\ln(99)}{v_0\cos(\alpha)}\right)}{\ln(0.99)}})-100g \cdot\ \dfrac{\ln\left(1-x\cdot\dfrac{\ln(100)-\ln(99)}{v_0\cos(\alpha)}\right)}{\ln(0.99)}$$

Here's a simulation of this graph where you can adjust the values: https://www.geogebra.org/calculator/awvet877
