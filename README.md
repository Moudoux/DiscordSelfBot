# DiscordSelfBot
A Java Discord selfbot that allows custom commands and rich messages (Embeds)

### Retrieving your access token

In browser or desktop Discord, type `Ctrl-Shift-I`. Go to the Application section, and and go to Storage > LocalStorage > discordapp.com. Find the token row, and your token will be the value in quotes. 

⚠ Do not share this token with anyone ⚠ This token provides complete access to your Discord account, so never share it!

### Installing and running

First download the source and compile it into a .jar file, set the main class to `me.alexander.discordbot.Main`

Now open a command prompt/terminal window and type `java -jar SelfBot.jar <Your access token>`

### Commands

```
/shutdown - Will shutdown the bot
/embed <Message> - Sends an embedded message
```

### Embedded message format

To send an embedded message you type `/embed <Message>`, in the message you can set the `title`, `body`, `footer` and an image (jpeg,jpg and png supported).

To add a title you simply type `**<Title>**`, to add a footer you type `--<Footer>--` and to add a image you just paste the url.

An example message might look like this: 

`/embed **This is the title** This is a test of the SelfBot --This is the footer--`

![Embedded Message](http://image.prntscr.com/image/673b1abeaee94bdaa108375c8935ecc4.png)
