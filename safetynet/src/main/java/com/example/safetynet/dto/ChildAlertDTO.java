package com.example.safetynet.dto;

import java.util.List;

public class ChildAlertDTO {
    private List<ChildDTO> children;

    public ChildAlertDTO() {
    }

    public ChildAlertDTO(List<ChildDTO> children) {
        this.children = children;
    }

    public List<ChildDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ChildDTO> children) {
        this.children = children;
    }

    public static class ChildDTO {
        private String firstName;
        private String lastName;
        private int age;
        private List<PersonInfoDTO> otherHouseMembers;

        public ChildDTO() {
        }

        public ChildDTO(String firstName, String lastName, int age, List<PersonInfoDTO> otherHouseMembers) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.otherHouseMembers = otherHouseMembers;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<PersonInfoDTO> getOtherHouseMembers() {
            return otherHouseMembers;
        }

        public void setOtherHouseMembers(List<PersonInfoDTO> otherHouseMembers) {
            this.otherHouseMembers = otherHouseMembers;
        }
    }
}
