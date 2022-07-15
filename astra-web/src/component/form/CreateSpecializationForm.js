import { useState } from "react";
import { create } from "../../service/specialization.service";
import Button from "../Button/Button";
import Input from "../Input/Input";
import LoaderBoundary from "../LoaderBoundary/LoaderBoundary";
import Spacer from "../Spacer/Spacer";

const CreateSpecializationForm = ({
    onSuccess = () => {},
    onFailure = () => {}
}) => {
    const [title, setTitle] = useState("");
    const [loading, setLoading] = useState(false);

    const createSpecialization = async () => {
        setLoading(true);
        const data = await create({
            title
        });

        onSuccess(data);
    }

    return (
        <div className="s-vflex-center modal-content">
            <Input withLabel={false} placeholder="Назва спеціалізації" value={title} onChange={event => setTitle(event.target.value)} />
            <Spacer height={20} />
            <div className="s-hflex-center">
                <LoaderBoundary condition={loading} size="small">
                    <Button isFilled={true} onClick={() => createSpecialization()}>
                        Підтвердити
                    </Button>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default CreateSpecializationForm;
