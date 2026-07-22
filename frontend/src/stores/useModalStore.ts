import {create} from "zustand";

interface ModalState {
    isOpen: boolean;
    modalBody: React.ReactNode;
    modalTitle: string;

    openModal: (body: React.ReactNode, title?: string) => void;
    closeModal: () => void;
}

export const useModalStore = create<ModalState>((set) => ({
    isOpen: false,
    modalBody: null,
    modalTitle: '',

    openModal: (content, title) => {
        console.log("opening modal");
        set({
            isOpen: true,
            modalBody: content,
            modalTitle: title,
        });
    },

    closeModal: () => {
        console.log("closing modal");
        set({
            isOpen: false,
            modalBody: null,
            modalTitle: '',
        })
    }
}));