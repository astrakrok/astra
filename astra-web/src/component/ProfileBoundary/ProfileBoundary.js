import {env} from "../../constant/env";
import {localStorageKey} from "../../constant/local.storage.key";
import {profile} from "../../constant/profile";
import {defaultUser} from "../../data/default/user";
import AxiosClient from "../AxiosClient/AxiosClient";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";

const ProfileBoundary = ({children}) => {
    if (env.profile === profile.PRODUCTION) {
        return (
            <AxiosClient>
                <PermissionBoundary>
                    {children}
                </PermissionBoundary>
            </AxiosClient>
        );
    }

    localStorage.setItem(localStorageKey.userData, JSON.stringify(defaultUser));
    return children;
}

export default ProfileBoundary;
