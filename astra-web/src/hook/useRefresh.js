import {useState} from "react"

const useRefresh = () => {
    const [state, setState] = useState(0);

    const refresh = () => {
        setState(previous => 1 - previous);
    }

    return [state, refresh];
}

export default useRefresh;
