# NendaRolas Inventory Value

RuneLite external plugin that shows Grand Exchange values directly on the normal inventory.

## Features

- Small GE value labels on normal inventory items.
- Stack value or one-item value mode.
- Inventory total shown on the inventory tab icon.
- Hover tooltip value for inventory items.
- Separate text sizes for item labels and the inventory total, plus configurable minimum shown value, value tiers, and tier colors.
- Optional untradeable item label.
- Excluded inventory slots, using formats like `1,2,5-8`.
- Uses the normal inventory interface only; bank, shop, and other side inventories are not mixed in.

## Configuration

- `Show inventory total`: show or hide the total value on the inventory icon.
- `Show item values`: show or hide labels on inventory items without disabling hover tooltips.
- `Show stack value`: show stack value instead of one-item value.
- `Show untradeable`: show a custom label under non-GE-tradeable items.
- `Show tooltip value`: show value in the inventory hover tooltip.
- `Item value text size`: change the size of labels drawn on inventory items.
- `Total value text size`: change the size of the total drawn on the inventory icon.
- `Excluded slots`: inventory slots to ignore, counted left-to-right and top-to-bottom from 1 to 28.
- `Tier limits` and `Tier colors`: customize the displayed colors for different value ranges.

## Bug Reports

Send bug reports or suggestions to `nendarolas@gmail.com`.

## Development

This project follows the RuneLite `example-plugin` structure used for Plugin Hub development.

RuneLite targets Java 11 for external plugins. Install JDK 11 or newer and run:

```sh
./gradlew build
./gradlew run
```

The `run` task starts RuneLite in developer mode with this plugin loaded.
