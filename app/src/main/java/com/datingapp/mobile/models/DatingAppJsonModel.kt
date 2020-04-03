package com.datingapp.mobile.models

data class Field(val name: String, val type: String, val value: String)
data class Action(
    val type: String,
    val name: String,
    val label: String,
    val method: String,
    val href: String,
    val fields: List<Field>
)

data class Link(val rel: String, val href: String)

data class DatingAppJsonModel(
    val type: String,
    val properties: HashMap<String, String>,
    val children: List<DatingAppJsonModel>,
    val links: List<Link>,
    val actions: List<Action>
)