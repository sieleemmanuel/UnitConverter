package com.siele.unitconverter.util
import androidx.compose.runtime.remember
import com.siele.unitconverter.R
import com.siele.unitconverter.data.model.UnitOfMeasure
object Constants {
    const val BASE_URL: String = "https://neutrinoapi.net/convert/"
    const val CURRENCY_BASE_URL: String = "https://api.api-ninjas.com/v1/convertcurrency"
    var unitsOfMeasure = listOf(
            UnitOfMeasure(R.drawable.ic_length,"Length"),
            UnitOfMeasure(R.drawable.ic_volume,"Volume"),
            UnitOfMeasure(R.drawable.ic_weight,"Weight"),
            UnitOfMeasure(R.drawable.ic_time,"Time"),
            UnitOfMeasure(R.drawable.ic_angle,"Angle"),
            UnitOfMeasure(R.drawable.ic_temperature,"Temperature"),
            UnitOfMeasure(R.drawable.ic_electricity,"Electricity"),
            UnitOfMeasure(R.drawable.ic_energy,"Energy"),
            UnitOfMeasure(R.drawable.ic_force,"Force"),
            UnitOfMeasure(R.drawable.ic_storage,"Storage"),
            UnitOfMeasure(R.drawable.currency,"Currency"),
            UnitOfMeasure(R.drawable.ic_crypto,"Crypto"),
        )

    val lengthUnits = listOf("Kilometer", "Meter", "Centimeter","Millimeter", "Micron",
        "Nanometers", "Mile", "Yard", "Feet", "Inch")
    val volumeUnits = listOf("Liter", "Milliliter", "Gallon", "Tablespoon","Teaspoon", "Cup", "Pint",
        "Cubic-centimeter", "Cubic-meter", "Cubic-inch", "Cubic-yard")
    val weightUnits = listOf("Kilogram", "Tonne","Gram", "Milligram", "Stone", "Pound", "Ounce",
    )
    val timeUnits = listOf("Year", "Month", "Week", "Day", "Hour", "Minute", "Second", "Millisecond",
    "Microsecond", "Nanosecond")
    val angleUnits = listOf("Degree", "Radian", "Milliradian", "Gradian", "Revolution", "Circle" )
    val temperatureUnits = listOf("Celsius", "Fahrenheit", "Kelvin")
    val electricCurrentUnits = listOf("Ampere", "Centiampere", "Gigampere", "Kiloampere", "Microampere",
        "Milliamp", "Megaampere", "Nanoampere")
    val energyUnits = listOf("Joule", "Kilojoule","Megajoule","Gigajoule","Calorie", "Kilocalorie",
        "Megacalorie", "Therm", "Quad", "Electronvolt", "Kilolectronvolt","Megaelectronvolt")
    val forceUnits = listOf("Newton", "Kilonewton", "Meganewton","Nanonewton", "Micronewton",
        "Millinetwon", "Dyne", "Poundal")
    val storageUnits  = listOf("Bit", "Kilobit","Megabit","Gigabit", "Terabit","Petabit",
        "Byte", "Kilobyte", "Megabyte", "Gigabyte", "Terabyte",
        "Petabyte")
    val currencyUnits = listOf("USD", "EUR", "EGP", "INR", "AED", "AFN", "ALL", "ARS", "AUD",
        "BDT","BGN", "BOB", "BRL", "CAD", "CHF", "CLP", "CNY", "COP", "CRC", "CZK", "DKK",
        "DZD",  "FJD",  "GEL", "GHS", "HKD", "HUF", "IDR", "ILS", "IQD", "ISK","JOD", "JPY",
        "KES", "KRW", "KWD", "KZI", "LBP", "LKR", "MAD","MDL", "MMK", "MNT", "MOP", "MXN", "MYR", "NGN",
        "NOK", "NZD", "PEN", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB","SAR", "SEK",
        "SGD", "THB", "TRY","TWD", "TZS", "UAH", "VEF", "VND", "ZAR")
    val cryptoUnits = listOf("BTC", "ETH", "LTC", "XMR", "XRP")

    }
