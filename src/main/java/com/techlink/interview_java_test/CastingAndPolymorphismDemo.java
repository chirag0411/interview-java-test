package com.techlink.interview_java_test;

// CastingAndPolymorphismDemo.java
// Covers: upcasting, downcasting, static/private/final/override,
// compile-time vs runtime behavior, and counter-question cases.

interface Animal {
    void sound();                            // abstract
    default void eat() {                     // default method (inherited)
        System.out.println("Animal: default eat()");
    }
    static void info() {                     // static method (not inherited)
        System.out.println("Animal: static info()");
    }
}

class Mammal implements Animal {
    @Override
    public void sound() { System.out.println("Mammal: generic sound()"); }

    public void sleep() { System.out.println("Mammal: sleep()"); }

    public final void breathe() {            // final = not overridable
        System.out.println("Mammal: final breathe()");
    }

    private void secret() {                  // private = not inherited
        System.out.println("Mammal: private secret()");
    }

    protected void callSecret() {            // calls private internally
        System.out.println("Mammal: calling private secret() internally ↓");
        secret();
    }

    public static void staticInfo() {        // static = hidden, not overridden
        System.out.println("Mammal: staticInfo()");
    }
}

class Dog extends Mammal {
    @Override
    public void sound() {                    // overridden (runtime polymorphism)
        System.out.println("Dog: barks");
    }

    public void wagTail() { System.out.println("Dog: wagTail()"); }

    // hides Mammal static
    public static void staticInfo() { System.out.println("Dog: staticInfo()"); }

    private void secret() {                  // new private method, not override
        System.out.println("Dog: private secret()");
    }
}

class Cat extends Mammal {
    @Override
    public void sound() { System.out.println("Cat: meows"); }
    public void scratch() { System.out.println("Cat: scratch()"); }
}

public class CastingAndPolymorphismDemo {
    public static void main(String[] args) {

        System.out.println("==== 1️⃣ Upcasting (Safe) ====");
        Dog dog = new Dog();                         // concrete child
        Mammal mammal = dog;                         // ✅ Upcasting
        mammal.sound();                              // Dog version (runtime)
        mammal.sleep();                              // Mammal
        mammal.breathe();                            // Mammal final
        // mammal.wagTail();                         // ❌ COMPILE-TIME: not visible in Mammal
        mammal.callSecret();                         // calls Mammal private internally
        Mammal.staticInfo();                         // Mammal static (compile-time)
        Dog.staticInfo();                            // Dog static (compile-time)
        Animal.info();                               // interface static
        mammal.eat();                                // interface default method

        System.out.println("\n==== 2️⃣ Downcasting (Valid) ====");
        Dog dogRef = (Dog) mammal;                   // ✅ Safe (actual Dog)
        dogRef.wagTail();                            // Dog specific
        dogRef.sound();                              // Dog (runtime)
        dogRef.staticInfo();                         // Dog static (compile-time)
        dogRef.breathe();                            // Mammal final

        System.out.println("\n==== 3️⃣ Invalid Downcasting (Runtime Error) ====");
        Mammal cat = new Cat();                      // upcast Cat → Mammal
        try {
            Dog wrong = (Dog) cat;                   // ⚠️ compiles, fails at runtime
            wrong.wagTail();                         // never runs
        } catch (ClassCastException e) {
            System.out.println("❌ Runtime Error: " + e);
        }

        System.out.println("\n==== 4️⃣ Compile-Time Errors (commented out) ====");
        // Dog badDog = new Mammal();                // ❌ CT: Mammal is not a Dog
        // Dog badCast = (Dog) new Mammal();         // ❌ CT: inconvertible types (compiler knows exact type)
        // mammal.wagTail();                         // ❌ CT: not in Mammal
        // mammal.secret();                          // ❌ CT: private not visible
        // dogRef.secret();                          // ❌ CT: private not visible
        // dogRef.sound(10);                         // ❌ CT: method not found

        System.out.println("\n==== 5️⃣ Safe Downcasting Using instanceof (Java 21 pattern) ====");
        Mammal unknown = getRandomMammal();
        if (unknown instanceof Dog d) {
            System.out.print("Found Dog → ");
            d.wagTail();
        } else if (unknown instanceof Cat c) {
            System.out.print("Found Cat → ");
            c.scratch();
        } else {
            System.out.println("Unknown type");
        }

        System.out.println("\n==== 6️⃣ Static vs Override Counter-Question Demo ====");
        Mammal mDog = new Dog();
        mDog.sound();                                // Dog (runtime)
        mDog.staticInfo();                           // Mammal (compile-time)
        Dog.staticInfo();                            // Dog (explicit)
        System.out.println("// Static resolved by reference type, not object");

        System.out.println("\n==== 7️⃣ Private vs Overridden Counter-Question ====");
        Dog d1 = new Dog();
        // d1.secret();                              // ❌ CT: private not visible
        d1.callSecret();                             // ✅ calls Mammal.secret() internally
        System.out.println("// Private methods are not inherited; new one in Dog is isolated");

        System.out.println("\n==== ✅ Program Completed ====");
    }

    private static Mammal getRandomMammal() {
        return System.currentTimeMillis() % 2 == 0 ? new Dog() : new Cat();
    }
}
