package org.sirox.ezprofile.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

class Config : OkaeriConfig() {

    @Comment("Plugin name displayed in the console")
    var pluginName: String = "EzProfile"

    @Comment("Should messages be sent to the console?")
    var logging: Boolean = true

}