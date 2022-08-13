import {page} from "./page";
import {userRole} from "./user.role";

const all = [
    userRole.guest,
    userRole.user,
    userRole.admin,
    userRole.superAdmin
];

const authorized = [
    userRole.user,
    userRole.admin,
    userRole.superAdmin
];

const notAuthorized = [
    userRole.guest
];

const admin = [
    userRole.admin,
    userRole.superAdmin
]

export const permission = {
    [page.home]: all,
    [page.profile]: [
        userRole.user
    ],
    [page.settings]: authorized,
    [page.login]: notAuthorized,
    [page.logout]: authorized,
    [page.register]: notAuthorized,
    [page.team]: [
        userRole.guest,
        userRole.user
    ],
    [page.joinTeam]: [
        userRole.guest,
        userRole.user
    ],
    [page.testing]: [
        userRole.user
    ],
    [page.admin]: admin,
    [page.admin.specializations.all]: admin,
    [page.admin.specializations.create]: admin,
    [page.admin.subjects.all]: admin,
    [page.admin.tests.all]: admin,
    [page.admin.tests.create]: admin,
    [page.admin.tests.id().edit]: admin,
    [page.admin.exams.all]: admin,
    [page.admin.testings.id().edit]: admin
};