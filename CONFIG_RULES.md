### 1. Root Config Class
It acts as a container for all config pages.  
It must be `public` and **not** `abstract`
### 2. Config Annotations
Config Annotations: `@Page`, `@Tab`, `@Group` and `@Option`.  
Each config field must use only **one** of these annotations.  
Annotated fields must be `public`, **not** `static` and **not** `final`.  
Unannotated fields are ignored.
### 3. Containers (`Page`, `Tab`, `Group`)
Containers represent sections in the config.  
A Container field:
- Must have a class type (not primitive, wrapper, `String` or `Enum`)
- Must be `public` and **not** `abstract`
### 4. Options
Options represent actual configurable values.  
Options must have a **non-null** value.  
Supported Types:
- Primitive type (`int`, `boolean`, ...)
- Wrapper (`Integer`, `Boolean`, ...)
- `String`
- `Enum`
- Any type extends `OptionTypeAdapter`
### 5. Placement
- `Page`: in the Root Config Class
- `Tab`: inside a Page
- `Group`: inside a Page or a Tab
- `Option`: inside a Page, Tab or Group