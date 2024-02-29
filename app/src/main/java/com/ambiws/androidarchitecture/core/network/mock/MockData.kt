package com.ambiws.androidarchitecture.core.network.mock

import com.ambiws.androidarchitecture.core.network.adapters.model.StatusCode
import com.ambiws.androidarchitecture.features.lists.user.data.response.UserResponse

object MockData {
    val errorBadRequest = MockErrorResponse(
        status = StatusCode.BAD_REQUEST.code,
        message = "Bad request",
        errors = null
    )

    val errorUnauthorized = MockErrorResponse(
        status = StatusCode.UNAUTHORIZED.code,
        message = "Unauthorized",
        errors = null
    )

    val errorServerUnexpected = MockErrorResponse(
        status = StatusCode.INTERNAL_SERVER_ERROR.code,
        message = null,
        errors = null
    )

    val users = listOf(
        /*
            Assuming that 'premium' users sorted on backed and emits first for better UX
            For opposite case we need to load whole list first, generate sublist of premium users,
            And finally handle pagination loading, excluding premium users (after they completely loaded)
         */
        UserResponse(
            id = 3002L,
            name = "Rob Smith",
            age = 32,
            gender = "Male",
            company = null,
            skills = null,
            address = null,
            bio = "Quisque ornare maximus facilisis. Etiam purus turpis, sagittis id sapien ut, ornare mattis metus. Nunc quis ex orci. In hac habitasse platea dictumst. Vestibulum nibh velit, molestie facilisis suscipit id, aliquet ac dui. Nullam consequat lobortis quam. Aenean egestas tellus id tellus varius rhoncus.",
            isPremium = true,
        ),
        UserResponse(
            id = 3003L,
            name = "Sarah Evans",
            age = 24,
            gender = "Female",
            company = null,
            skills = null,
            address = null,
            bio = "Pellentesque condimentum dolor eget congue mollis. Nunc vestibulum magna purus, a vehicula sem consequat ut. Donec nec libero vehicula, eleifend orci nec, pulvinar elit. Donec dignissim hendrerit ullamcorper. Donec sodales fermentum justo quis tincidunt. Aenean risus lorem, interdum eget mollis at, dignissim ut magna. Duis cursus libero ante, euismod rhoncus mauris molestie et. Sed ut risus vel nibh pretium dignissim eu at tellus.",
            isPremium = true,
        ),
        UserResponse(
            id = 4011L,
            name = "Nata Jones",
            age = 23,
            gender = "Female",
            company = null,
            skills = null,
            address = null,
            bio = "Etiam purus turpis, sagittis id sapien ut, ornare mattis metus.",
            isPremium = true,
        ),
        UserResponse(
            id = 2089L,
            name = "Sarah Bright",
            age = 23,
            gender = "Female",
            company = null,
            skills = listOf(
                "Kotlin",
                "Architecture",
                "CI/CD"
            ),
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 2177L,
            name = "Nick Fatale",
            age = 44,
            gender = "Male",
            company = "Google",
            skills = listOf(
                "Data structures",
                "Algorithms"
            ),
            address = "4455 Landing Lange, APT 4, Louisville, KY 40018-1234",
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 3001L,
            name = "Suzy Nolan",
            age = 40,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4000L,
            name = "DevUser A",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4001L,
            name = "DevUser B",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4002L,
            name = "DevUser C",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4003L,
            name = "DevUser D",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4004L,
            name = "DevUser E",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4005L,
            name = "DevUser F",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4006L,
            name = "DevUser G",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4007L,
            name = "DevUser H",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4008L,
            name = "DevUser I",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4009L,
            name = "DevUser J",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
        UserResponse(
            id = 4010L,
            name = "DevUser K",
            age = 1,
            gender = null,
            company = null,
            skills = null,
            address = null,
            bio = null,
            isPremium = false,
        ),
    )

    val usersId = users.map {
        it.id
    }
}
