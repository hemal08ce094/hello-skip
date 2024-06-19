// Copyright 2023 Skip
//
// This is free software: you can redistribute and/or modify it
// under the terms of the GNU Lesser General Public License 3.0
// as published by the Free Software Foundation https://fsf.org

package skip.ui

import skip.lib.*

import skip.foundation.*

import androidx.compose.ui.text.input.KeyboardType

internal val logger: SkipLogger = SkipLogger(subsystem = "skip.ui", category = "SkipUI") // adb logcat '*:S' 'skip.ui.SkipUI:V'

enum class UIKeyboardType(override val rawValue: Int, @Suppress("UNUSED_PARAMETER") unusedp: Nothing? = null): RawRepresentable<Int> {
    default(0),
    asciiCapable(1),
    numbersAndPunctuation(2),
    URL(3),
    numberPad(4),
    phonePad(5),
    namePhonePad(6),
    emailAddress(7),
    decimalPad(8),
    twitter(9),
    webSearch(10),
    asciiCapableNumberPad(11),
    alphabet(12);

    internal fun asComposeKeyboardType(): KeyboardType {
        when (this) {
            UIKeyboardType.default -> return KeyboardType.Text.sref()
            UIKeyboardType.asciiCapable -> return KeyboardType.Ascii.sref()
            UIKeyboardType.numbersAndPunctuation -> return KeyboardType.Text.sref()
            UIKeyboardType.URL -> return KeyboardType.Uri.sref()
            UIKeyboardType.numberPad -> return KeyboardType.Number.sref()
            UIKeyboardType.phonePad -> return KeyboardType.Phone.sref()
            UIKeyboardType.namePhonePad -> return KeyboardType.Text.sref()
            UIKeyboardType.emailAddress -> return KeyboardType.Email.sref()
            UIKeyboardType.decimalPad -> return KeyboardType.Decimal.sref()
            UIKeyboardType.twitter -> return KeyboardType.Text.sref()
            UIKeyboardType.webSearch -> return KeyboardType.Text.sref()
            UIKeyboardType.asciiCapableNumberPad -> return KeyboardType.Text.sref()
            UIKeyboardType.alphabet -> return KeyboardType.Text.sref()
        }
    }

    companion object {
    }
}

fun UIKeyboardType(rawValue: Int): UIKeyboardType? {
    return when (rawValue) {
        0 -> UIKeyboardType.default
        1 -> UIKeyboardType.asciiCapable
        2 -> UIKeyboardType.numbersAndPunctuation
        3 -> UIKeyboardType.URL
        4 -> UIKeyboardType.numberPad
        5 -> UIKeyboardType.phonePad
        6 -> UIKeyboardType.namePhonePad
        7 -> UIKeyboardType.emailAddress
        8 -> UIKeyboardType.decimalPad
        9 -> UIKeyboardType.twitter
        10 -> UIKeyboardType.webSearch
        11 -> UIKeyboardType.asciiCapableNumberPad
        12 -> UIKeyboardType.alphabet
        else -> null
    }
}

class UITextContentType: RawRepresentable<Int> {
    override val rawValue: Int

    constructor(rawValue: Int) {
        this.rawValue = rawValue
    }

    override fun equals(other: Any?): Boolean {
        if (other !is UITextContentType) return false
        return rawValue == other.rawValue
    }

    companion object {

        val name = UITextContentType(rawValue = 0) // Not allowed as a Kotlin enum case name
        val namePrefix = UITextContentType(rawValue = 1)
        val givenName = UITextContentType(rawValue = 2)
        val middleName = UITextContentType(rawValue = 3)
        val familyName = UITextContentType(rawValue = 4)
        val nameSuffix = UITextContentType(rawValue = 5)
        val nickname = UITextContentType(rawValue = 6)
        val jobTitle = UITextContentType(rawValue = 7)
        val organizationName = UITextContentType(rawValue = 8)
        val location = UITextContentType(rawValue = 9)
        val fullStreetAddress = UITextContentType(rawValue = 10)
        val streetAddressLine1 = UITextContentType(rawValue = 11)
        val streetAddressLine2 = UITextContentType(rawValue = 12)
        val addressCity = UITextContentType(rawValue = 13)
        val addressState = UITextContentType(rawValue = 14)
        val addressCityAndState = UITextContentType(rawValue = 15)
        val sublocality = UITextContentType(rawValue = 16)
        val countryName = UITextContentType(rawValue = 17)
        val postalCode = UITextContentType(rawValue = 18)
        val telephoneNumber = UITextContentType(rawValue = 19)
        val emailAddress = UITextContentType(rawValue = 20)
        val URL = UITextContentType(rawValue = 21)
        val creditCardNumber = UITextContentType(rawValue = 22)
        val username = UITextContentType(rawValue = 23)
        val password = UITextContentType(rawValue = 24)
        val newPassword = UITextContentType(rawValue = 25)
        val oneTimeCode = UITextContentType(rawValue = 26)
        val shipmentTrackingNumber = UITextContentType(rawValue = 27)
        val flightNumber = UITextContentType(rawValue = 28)
        val dateTime = UITextContentType(rawValue = 29)
        val birthdate = UITextContentType(rawValue = 30)
        val birthdateDay = UITextContentType(rawValue = 31)
        val birthdateMonth = UITextContentType(rawValue = 32)
        val birthdateYear = UITextContentType(rawValue = 33)
        val creditCardSecurityCode = UITextContentType(rawValue = 34)
        val creditCardName = UITextContentType(rawValue = 35)
        val creditCardGivenName = UITextContentType(rawValue = 36)
        val creditCardMiddleName = UITextContentType(rawValue = 37)
        val creditCardFamilyName = UITextContentType(rawValue = 38)
        val creditCardExpiration = UITextContentType(rawValue = 39)
        val creditCardExpirationMonth = UITextContentType(rawValue = 40)
        val creditCardExpirationYear = UITextContentType(rawValue = 41)
        val creditCardType = UITextContentType(rawValue = 42)
    }
}

interface UIFeedbackGenerator {

    // note that this needs AndroidManifest.xml permission:
    // <uses-permission android:name="android.permission.VIBRATE"/>
    val vibrator: android.os.Vibrator?
        get() {
            val context = ProcessInfo.processInfo.androidContext.sref() // Android-specific extension to get the global Context
            val vibratorManager_0 = (context.getSystemService(android.content.Context.VIBRATOR_MANAGER_SERVICE) as? android.os.VibratorManager).sref()
            if (vibratorManager_0 == null) {
                logger.log("vibratorManager: returned null")
                return null
            }

            logger.log("vibratorManager: ${vibratorManager_0}")

            // https://developer.android.com/reference/android/os/Vibrator
            return vibratorManager_0.getDefaultVibrator()
        }
}

/// UIImpactFeedbackGenerator is used to give user feedback when an impact between UI elements occurs
open class UIImpactFeedbackGenerator: UIFeedbackGenerator {
    private val style: UIImpactFeedbackGenerator.FeedbackStyle

    constructor() {
        this.style = UIImpactFeedbackGenerator.FeedbackStyle.medium
    }

    constructor(style: UIImpactFeedbackGenerator.FeedbackStyle) {
        this.style = style
    }

    /// call when your UI element impacts something else
    open fun impactOccurred() {
        this.vibrator?.vibrate(style.vibrationEffect)
    }

    /// call when your UI element impacts something else with a specific intensity [0.0, 1.0]
    open fun impactOccurred(intensity: Double) {
        if (intensity <= 0.0) {
            return
        }

        val effect = android.os.VibrationEffect.startComposition()
            .addPrimitive(android.os.VibrationEffect.Composition.PRIMITIVE_CLICK, Float(intensity), 0)
            .compose()
        this.vibrator?.vibrate(effect)
    }

    open fun impactOccurred(intensity: Double, at: CGPoint) {
        val location = at
        impactOccurred(intensity = intensity)
    }

    enum class FeedbackStyle(override val rawValue: Int, @Suppress("UNUSED_PARAMETER") unusedp: Nothing? = null): Sendable, RawRepresentable<Int> {
        light(0),
        medium(1),
        heavy(2),

        soft(3),
        rigid(4);

        internal val vibrationEffect: android.os.VibrationEffect
            get() {
                when (this) {
                    UIImpactFeedbackGenerator.FeedbackStyle.light -> return android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_TICK)
                    UIImpactFeedbackGenerator.FeedbackStyle.medium -> return android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_CLICK)
                    UIImpactFeedbackGenerator.FeedbackStyle.heavy -> return android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_HEAVY_CLICK)
                    UIImpactFeedbackGenerator.FeedbackStyle.soft -> return android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_TICK)
                    UIImpactFeedbackGenerator.FeedbackStyle.rigid -> return android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_CLICK)
                }
            }

        companion object {
        }
    }

    companion object: CompanionClass() {

        override fun FeedbackStyle(rawValue: Int): UIImpactFeedbackGenerator.FeedbackStyle? {
            return when (rawValue) {
                0 -> FeedbackStyle.light
                1 -> FeedbackStyle.medium
                2 -> FeedbackStyle.heavy
                3 -> FeedbackStyle.soft
                4 -> FeedbackStyle.rigid
                else -> null
            }
        }
    }
    open class CompanionClass {
        open fun FeedbackStyle(rawValue: Int): UIImpactFeedbackGenerator.FeedbackStyle? = UIImpactFeedbackGenerator.FeedbackStyle(rawValue = rawValue)
    }
}

/// UINotificationFeedbackGenerator is used to give user feedback when an notification is displayed
open class UINotificationFeedbackGenerator: UIFeedbackGenerator {

    constructor() {
    }

    /// call when a notification is displayed, passing the corresponding type
    open fun notificationOccurred(notificationType: UINotificationFeedbackGenerator.FeedbackType) {
        // amplitude parameter: “The strength of the vibration. This must be a value between 1 and 255”
        this.vibrator?.vibrate(notificationType.vibrationEffect)
    }

    /// call when a notification is displayed, passing the corresponding type
    open fun notificationOccurred(notificationType: UINotificationFeedbackGenerator.FeedbackType, at: CGPoint) {
        val location = at
        notificationOccurred(notificationType)
    }

    enum class FeedbackType(override val rawValue: Int, @Suppress("UNUSED_PARAMETER") unusedp: Nothing? = null): Sendable, RawRepresentable<Int> {
        success(0),
        warning(1),
        error(2);

        internal val vibrationEffect: android.os.VibrationEffect
            get() {
                when (this) {
                    UINotificationFeedbackGenerator.FeedbackType.success -> {
                        return android.os.VibrationEffect.startComposition()
                            .addPrimitive(android.os.VibrationEffect.Composition.PRIMITIVE_CLICK, 0.8f, 0)
                            .addPrimitive(android.os.VibrationEffect.Composition.PRIMITIVE_CLICK, 0.8f, 150)
                            .compose()
                    }
                    UINotificationFeedbackGenerator.FeedbackType.warning -> {
                        return android.os.VibrationEffect.startComposition()
                            .addPrimitive(android.os.VibrationEffect.Composition.PRIMITIVE_CLICK, 0.8f, 0)
                            .addPrimitive(android.os.VibrationEffect.Composition.PRIMITIVE_CLICK, 0.8f, 150)
                            .compose()
                    }
                    UINotificationFeedbackGenerator.FeedbackType.error -> {
                        return android.os.VibrationEffect.startComposition()
                            .addPrimitive(android.os.VibrationEffect.Composition.PRIMITIVE_CLICK, 0.5f, 0)
                            .addPrimitive(android.os.VibrationEffect.Composition.PRIMITIVE_CLICK, 0.7f, 100)
                            .addPrimitive(android.os.VibrationEffect.Composition.PRIMITIVE_CLICK, 0.9f, 150)
                            .compose()
                    }
                }
            }

        companion object {
        }
    }

    companion object: CompanionClass() {

        override fun FeedbackType(rawValue: Int): UINotificationFeedbackGenerator.FeedbackType? {
            return when (rawValue) {
                0 -> FeedbackType.success
                1 -> FeedbackType.warning
                2 -> FeedbackType.error
                else -> null
            }
        }
    }
    open class CompanionClass {
        open fun FeedbackType(rawValue: Int): UINotificationFeedbackGenerator.FeedbackType? = UINotificationFeedbackGenerator.FeedbackType(rawValue = rawValue)
    }
}


/// UINotificationFeedbackGenerator is used to give user feedback when an notification is displayed
open class UISelectionFeedbackGenerator: UIFeedbackGenerator {

    constructor() {
    }

    /// call when a notification is displayed, passing the corresponding type
    open fun selectionChanged() {
        this.vibrator?.vibrate(android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_TICK))
    }

    open fun selectionChanged(at: CGPoint) {
        val location = at
        selectionChanged()
    }


    companion object: CompanionClass() {
    }
    open class CompanionClass {
    }
}

