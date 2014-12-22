SMS and Call Filter
==================


SMS and Call filter is a fork project of SMS Filter by Jelly Greets.


License
=======


>  Author: Hasib Al Muhaimin.

>  The contents of this file are subject to the Mozilla Public License
  Version 1.1 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.mozilla.org/MPL/

  ***Courtesy to Jelle Geerts:***
>  Author: Jelle Geerts

>  Usage of the works is permitted provided that this instrument is
  retained with the works, so that any entity that uses the works is
  notified of this instrument.

>  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.


Features:
=========


 * Just Install and enjoy.
 * If Mobile no is less than 5 digits, It will get blocked.
 * It will block a SMS if it think the sms is spam/promotional/unusual.
 * User can turn off notification.
 * Do block sms from user who hide their mobile no.
 * User can add Custom Filters for messages.
 * Can restore messages to inbox.
 * No ad.

Change Log:
==========

####Version 1.4.8
* Few Numbers has been added in the block list by default. For incoming call only.
* Bangla to English Converter added. So we can train on Bangla message now!
* Contribution link added.

####Version 1.4.7
* Update Weight vector.
* Change of Hash Function and HashFullString Function.
  Idea of Bloom Filter added.
* Message can be restored from MessageViewer.
* Major change in SKSpamBlocker.java.


####Version 1.4.6
* Update Weight vector for Neural Netowork. (reduced overlapping
  messages in DATABASE for training)
* Show Notification on Delete message from 'MessageList' and
  show notification on any error while restoring message to
  inbox.


####Version 1.4.5
* Update Weight vector for Neural Netowork.

####Version 1.4.4

* Now call will get blocked if number is less than '5' digits. Previously the threshold was *less than '6' digits*.
* Change License.
* Minor Bug fix.

####Version 1.4.3

* Blocks sms from user who hides their mobile no.


####Version 1.4.2

* Added about page with Licence and courtesy.



####Version 1.4.1

* Fix a bug in pendingintent. now the request id will be equal to
  message id mod 2^28.

####Version 1.4


* Fix a bug about PendingIntent for Notification bar.
* Added Neural Network.
* Added feature to restore message to inbox.
* Change of app Icon.
* Change feature. A spam message will always be saved
  User can only change settings to show notification or not.
* Added call filter. New permission is needed from now on.

####Version 1.3


* Fix code so it still compiles on Android 2.2.

####Version 1.2


* Show a warning when the application is run on Android 4.4 or newer,
  as the application doesn't work on 4.4, and possibly doesn't work on
  newer versions either.
  See https://code.google.com/p/android/issues/detail?id=61684 .

####Version 1.1


* New feature: entering #ANY# as the filter address creates a filter
  that applies to any address.

####Version 1.0


* First release.
