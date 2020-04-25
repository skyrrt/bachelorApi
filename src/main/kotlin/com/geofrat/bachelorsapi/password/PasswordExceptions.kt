package com.geofrat.bachelorsapi.password

class PasswordAlreadyExistsException(message: String) : Exception(message)

class PasswordNotFoundException(message: String) : Exception(message)