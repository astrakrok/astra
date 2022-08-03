import {userRole} from "../../constant/user.role";

export const defaultUser = {
    roles: [userRole.admin],
    name: "Anonymous",
    surname: "Anonymous",
    course: null,
    school: null,
    email: "empty@email.com",
    pictureUrl: "/images/avatar-1.png"
}
