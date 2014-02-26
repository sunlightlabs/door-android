## Sunlight Foundation door opener

Opens the door to the Sunlight office. Can be used from anywhere in the world to open the door.


### Security

**_"You open sourced your office door opener??""_**

Your system's not secure unless it's open! Our office remains secure because:

* Compiling the app so it can find the door opening service requires a secret URL.
* If you have the app installed, your device must still be whitelisted via a private CMS.
* Even if your device is whitelisted, you must know the PIN to gain entry (for the first time only).
* Just for the hell of it, that private CMS is only accessible via office WiFi.

Public methods, private keys.


### Setup

Copy `urls.xml.example` to `res/values/urls.xml` and fill in the full URL endpoint of the door opening service.

Include the protocol (`https://` **strongly recommended**), domain name, path, and any trailing slashes needed. Whatever URL you put here is going to get a question mark appended directly after it.


## Public domain

This project is [dedicated to the public domain](LICENSE). As spelled out in [CONTRIBUTING](CONTRIBUTING.md):

> The project is in the public domain within the United States, and copyright and related rights in the work worldwide are waived through the [CC0 1.0 Universal public domain dedication](http://creativecommons.org/publicdomain/zero/1.0/).

> All contributions to this project will be released under the CC0 dedication. By submitting a pull request, you are agreeing to comply with this waiver of copyright interest.
