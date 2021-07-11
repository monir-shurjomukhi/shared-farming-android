package com.pranisheba.sharedfarming.model


data class FundOpportunity (

	val id : Int,
	val location : Location,
	val breed : Breed,
	val created : String,
	val modified : String,
	val category : String,
	val image : String,
	val name : String,
	val amount : Double,
	val duration : Int,
	val profit_percentage : Double,
	val shariah_profit_from : String,
	val shariah_profit_to : String,
	val gender : String,
	val average_weight : Double,
	val source : String,
	val growth_timeline : String,
	val details : String,
	val is_active : Boolean,
	val number_of_cows : Int,
	val faq : Int
)