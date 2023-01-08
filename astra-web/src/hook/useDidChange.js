import {useEffect, useRef} from "react"

const useDidChange = (callback, dependencies) => {
    const didChange = useRef(false);

    useEffect(() => {
        if (didChange.current) {
            callback();
        } else {
            didChange.current = true;
        }
    }, dependencies);
}

export default useDidChange;
