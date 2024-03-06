# ProjectileTrajectories
A Minecraft Paper plugin written in Kotlin that allows players to see the trajectories of different projectiles in real time.
![image](https://user-images.githubusercontent.com/44951826/177647850-74c5b76f-50d9-4b0a-9c77-ac13dc90108a.png)

The trajectory of a projectile in Minecraft with friction coefficient of $0.99/\frac{\text{1}}{\text{tick}}$ and
initial velocity $v_0/\frac{\text{blocks}}{\text{tick}}$ with angle $\alpha/\degree$ and gravitational acceleration of $g/\frac{\text{blocks}}{\text{tick}^2}$ can be modeled by the following equation:

$$y(x)=\dfrac{v\sin(\alpha) + 100g}{\ln(100)-\ln(99)}\cdot\ (1-0.99^{\dfrac{\ln\left(1-\dfrac{x(\ln(100)-\ln(99))}{v\cos(\alpha)}\right)}{\ln(0.99)}})-100g \cdot\ \dfrac{\ln\left(1-\dfrac{x(\ln(100)-\ln(99))}{v\cos(\alpha)}\right)}{\ln(0.99)}$$
