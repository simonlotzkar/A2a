# Game of Life v1.1
## Simulation of lifeforms.
<p>This simulates a population of various lifeforms. Slider changes turns per click.</p>

### Lifeform Rules
#### Omnivores
- choose spaces with food over empty ones
- reproduce with 1 mate and 1 space next to them
- have max hunger of 12
#### Carnivores
- choose random spaces
- reproduce with 1 mate and 2 spaces next to them
- have max hunger 20
- can eat plants but doesn't reset hunger
#### Herbivores
- choose random spaces
- reproduce with 1 mate and 2 spaces next to them
- have max hunger 5
#### Omnivores, Carnivores, and Herbivores
- can move 1 space next to them in any direction
- babies are 25% full
- can reproduce 1 space next to them in any direction
- can't eat their own kind
#### Plants
- reproduce with 1 mate and 2 spaces next to them
- can reproduce up to 2 spaces away in any direction