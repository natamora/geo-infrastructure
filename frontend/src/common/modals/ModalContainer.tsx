import { Modal } from '@mantine/core';
import { useModalStore } from '../../stores/useModalStore.ts';

export default function ModalContainer() {
    const { isOpen, modalBody, modalTitle, closeModal } = useModalStore();

    return (
        <Modal
            opened={isOpen}
            onClose={closeModal}
            title={modalTitle}
            zIndex={100000}
        >
            {modalBody}
        </Modal>
    );
}