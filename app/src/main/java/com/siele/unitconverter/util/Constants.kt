package com.siele.unitconverter.util
import com.siele.unitconverter.R
import com.siele.unitconverter.data.model.UnitOfMeasure
object Constants {
        var unitsOfMeasure = listOf(
            UnitOfMeasure(R.drawable.length,"Length"),
            UnitOfMeasure(R.drawable.volume,"Volume"),
            UnitOfMeasure(R.drawable.weight,"Weight"),
            UnitOfMeasure(R.drawable.time,"Time"),
            UnitOfMeasure(R.drawable.angle,"Angle"),
            UnitOfMeasure(R.drawable.temperature,"Temperature"),
            UnitOfMeasure(R.drawable.electricity,"Electricity"),
            UnitOfMeasure(R.drawable.energy,"Energy"),
            UnitOfMeasure(R.drawable.force,"Force"),
            UnitOfMeasure(R.drawable.storage,"Storage"),
            UnitOfMeasure(R.drawable.currency,"Currency"),
            UnitOfMeasure(R.drawable.crypto,"Crypto"),
        )

    val lengthUnits = listOf("Kilometers", "Meters", "Centimeters","Millimeters", "Micrometers",
        "Nanometers", "Mile", "Yard", "Feet", "Inches")
    val volumeUnits = listOf("Liter", "Milliliter", "Gallon", "Tablespoon","Teaspoon", "Cup", "Pint",
        "Cubic-centimeter", "Cubic-meter", "Cubic-inch", "Cubic-yard")
    val weightUnits = listOf("Kilogram", "Tonne","Gram", "Milligram", "Pound", "Ounce", "Calorie",
        "Carat")
    val timeUnits = listOf("Year", "Month", "Week", "Day", "Hour", "Minute", "Second", "Millisecond",
    "Microsecond", "Nanosecond")
    val angleUnits = listOf("Degree", "Radian", "Milliradian", "Gradian", "Revolution", "Circle" )
    val temperatureUnits = listOf("Celsius", "Fahrenheit", "Kelvin")
    val electricUnits = listOf("Volt", "Amp", "Ohm", "Megohm", "Microhm")
    val energyUnits = listOf("Joule", "Kilojoule","Megajoule","Gigajoule","Calorie", "Kilocalorie",
        "Megacalorie", "Therms", "Quads", "Electronvolts", "Kilolectronvolts","Megaelectronvolts")
    val forceUnits = listOf("Newton", "Kilonewton", "Meganewton","Nanonewton", "Micronewton",
        "Millinetwon", "Dyne", "Poundal", "Kilogram-force", "Gram-force", "Pound-force", "Ounce-force")
    val storageUnits  = listOf("Bit", "Bytes", "Kilobytes", "Megabyte", "Gigabyte", "Terabyte",
        "Petabyte")
    val currencyUnits= listOf("USD", "EUR", "INR", "AED", "AFN", "ALL", "ARS", "AUD",
        "BDT","BGN", "BOB", "BRL", "CAD", "CHF", "CLP", "CNY", "COP", "CRC", "CZK", "DKK",
        "DZD", "EGP", "FJD",  "GEL", "GHS", "HKD", "HUF", "IDR", "ILS", "IQD", "ISK","JOD", "JPY",
        "KRW", "KWD", "KZI", "LBP", "LKR", "MAD","MDL", "MMK", "MNT", "MOP", "MXN", "MYR", "NGN",
        "NOK", "NZD", "PEN", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB","SAR", "SEK",
        "SGD", "THB", "TRY","TWD", "TZS", "UAH", "VEF", "VND", "ZAR")
    val cryptoUnits = listOf("BTC", "ETC", "LTC", "XMR", "XRP")

    }
