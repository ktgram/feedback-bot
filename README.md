# How to use

Feedback Bot, allows you to make a bot in which users can directly contact you and communicate as in private messages.

To make it work, change the token to your own, add the bot to the chat, give it administrative rights.

#### Attention. Note that the chat should be a supergroup where tokens are included for it to work correctly.

When some user writes to the bot, it will create a separate topic for him and forward all his messages there, and your
responses to this topic to duplicate the user.
Also, if you close the topic, the user will not be able to write messages to you (ban the user from feedback), in order
to allow it back you need to reopen the topic.

# How to run

run your first bot with command:

```
./gradlew run
```

If you have any questions, you can see the [api documentation](https://vendelieu.github.io/telegram-bot/) or ask in
the [chat room](https://t.me/venny_tgbot).

### Acknowledgements

Thanks for choosing this library, I hope you find it handy and useful!

If you have any suggestions or feedback, I'll always be glad to help and listen to you :)