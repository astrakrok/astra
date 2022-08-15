import {userRole} from "../../constant/user.role";

export const defaultPictureUrl = "/images/default/avatar.png";

export const defaultUser = {
    roles: [userRole.guest],
    name: "Anonymous",
    surname: "Anonymous",
    course: null,
    school: null,
    email: "empty@email.com"
}
