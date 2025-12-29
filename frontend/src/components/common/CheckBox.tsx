import * as React from "react";
import cn from "@/shared/utils/cn";

export interface CheckboxProps extends React.InputHTMLAttributes<HTMLInputElement> {
  containerClassName?: string
  checkboxClassName?: string
  textClassName?: string
  text: React.ReactNode
}

const Checkbox = React.forwardRef<HTMLInputElement, CheckboxProps>(
  ({ containerClassName,checkboxClassName, textClassName,name, text ,id, type, ...props }, ref) => {
    return (
      <div className={cn("flex items-center space-x-2", containerClassName,)}>
        <input
          type="checkbox"
          className={
            cn("flex items-center justify-center h-4 w-4 appearance-none rounded-sm border border-primary outline-none cursor-pointer",
              "checked:before:content-[''] checked:before:w-2.5 checked:before:h-2.5 checked:before:rounded-full checked:before:bg-primary",
              checkboxClassName,
            )}
          id={id}
          name={name}
          ref={ref}
          {...props}
        />
        <label
          htmlFor={id}
          className={
            cn("text-sm font-normal cursor-pointer leading-none " +
              "peer-disabled:cursor-not-allowed peer-disabled:opacity-70 ",
              textClassName)}>
          {text}
        </label>
      </div>
    );
  },
);
Checkbox.displayName = "Checkbox";

export { Checkbox };