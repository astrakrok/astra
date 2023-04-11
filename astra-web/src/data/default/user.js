import {env} from "../../constant/env";
import {profile} from "../../constant/profile";
import {userRole} from "../../constant/user.role";

export const defaultPictureUrl = "/images/default/avatar.png";

export const defaultUser = env.profile === profile.PRODUCTION ? {
    roles: [userRole.guest],
    name: "Anonymous",
    surname: "Anonymous",
    course: null,
    school: null,
    email: "empty@email.com"
} : {
    roles: [userRole.user, userRole.admin, userRole.superAdmin],
    name: "Mock",
    surname: "Mockovich",
    course: null,
    school: null,
    email: "mock@email.com"
};
