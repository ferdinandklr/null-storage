# NullStorage plugin

This minecraft plugin adds *null storage crates* 📦 on your vanilla server.
It will work best with [Paper](https://github.com/PaperMC/Paper) installed (btw you should always use paper, there is no reason not to).

The only minecraft version I tested is currently **1.16**, but it should work pretty much as is on recent adjacent versions, like **1.15**, **1.14**, or the upcoming **1.17**.

I want to give special thanks to [TheTurkeyDev](https://github.com/TheTurkeyDev/) for this idea of plugin, you can try his version [here](https://github.com/TheTurkeyDev/vanilla-null).

## TL;DR

If you just want to try/install the plugin, you can download it by clicking on the releases button on the right (if github didn't changed).
Then simply drop it in your /plugins folder, and your are good to go.

## But I absolutly want to compile it myself !

Then download the github zip, and compile it on your computer. I assume that if you want to do such a thing, you will know how to do it by yourself 🤷.

## What are the differences between this version and [TheTurkeyDev's](https://github.com/TheTurkeyDev/) one ?

Well, I didn't simply fork his project because after readind the code, I wanted to do it quite differently, so there would be way too many code revamp for a fork to make sense. First, I didn't locked myself to shulkers, and I prefered to store all the data in metadata tags. This way I don't need databases and heavy calculations to check if the item is destroy at some point or not etc. Secondly, I make sure that null crates can only contain certain items *(dirt, cobblestone, ...)*, and not any item, to nerf a little bit this cheaty item. In fact, if you could put shulkers in it, you could pretty much create infinite storage in a single block, and I didn't want that.

This code doesn't posses any config file as I don't want to loose time doing this. If you want to customize the plugin, you can either contact me by email or edit it by yourself (this code is completly open to use to anyone, for free).